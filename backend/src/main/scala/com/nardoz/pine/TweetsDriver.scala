
package com.nardoz.pine

import org.apache.spark._
import org.apache.spark.SparkContext._

import com.datastax.spark.connector._
import com.nardoz.pine.Utils._

object TweetsDriver extends App {
  val ks = "nardoz_pine"

  if (args.length != 1 && args.length != 2) {
    println("Usage: TweetsDriver <filename> [cassandra-node]")
    sys.exit(1)
  }

  val filename = args(0)
  val hostname = if (args.length < 2) "localhost" else args(1)

  val conf = new SparkConf()
  conf.setAppName("Nardoz.Pine TweetsDriver")
  conf.set("spark.cassandra.connection.host", hostname)
  val sc = new SparkContext(conf)

  //  val filename = "../data/tweets.json.gz"
  val jsons = sc.textFile(filename)
  val data = jsons.flatMap(fromJsonToDataPoint)

  val tweets = data.map { d =>
    (d.tweetId, Tweet(d.tweetId, roundDownByMinute(d.createdAt), d.screenName, d.pic, d.followersCount))
  }

  // --------------------------------
  // ReTweet logic

  val rts = data.
    filter(d => d.retweetedStatusId > 0). // only those with RTs
    map { d =>

    (d.retweetedStatusId, // Tweet Original
      Tweet(d.tweetId, roundDownByMinute(d.createdAt), d.screenName, d.pic, d.followersCount) // this is the ReTweet
      )
  }

  val tweetsWithRTs = rts.join(tweets)

  val rtsByTweetIdByMinute = tweetsWithRTs.groupBy {
    case (tweetId, (rtTweet, originalTweet)) => (tweetId, originalTweet.screenName, rtTweet.createdAt)
  }

  val rtsByTweetIdByMinuteCount = rtsByTweetIdByMinute.map {
    case ((tweetId, screenName, createdAt), it) => TweetStats(tweetId, createdAt, screenName, it.size)
  }

  rtsByTweetIdByMinuteCount.saveToCassandra(ks, "rts_tweet_stats")

  val flockRts = rtsByTweetIdByMinute.flatMap {
    case ((tweetId, screenName, createdAt), it) => it.map {
      case (tweetId, (rtTweet, originalTweet)) =>

        Flock(tweetId, createdAt, screenName, rtTweet.screenName, rtTweet.pic, rtTweet.followersCount)
    }
  }

  flockRts.saveToCassandra(ks, "rts_flock")

  // --------------------------------
  // REPLIES logic

  val replies = data.filter(d => d.inReplyToStatusId > 0).map { d =>
    (d.inReplyToStatusId, Tweet(d.tweetId, roundDownByMinute(d.createdAt), d.screenName, d.pic, d.followersCount))
  }

  val tweetsWithReplies = replies.join(tweets)

  val repliesByTweetIdByMinute = tweetsWithReplies.groupBy {
    case (tweetId, (replyTweet, originalTweet)) => (tweetId, originalTweet.screenName, replyTweet.createdAt)
  }

  val repliesByTweetIdByMinuteCount = repliesByTweetIdByMinute.map {
    case ((tweetId, screenName, createdAt), it) => TweetStats(tweetId, createdAt, screenName, it.size)
  }

  repliesByTweetIdByMinuteCount.saveToCassandra(ks, "replies_tweet_stats")

  val flockReplies = repliesByTweetIdByMinute.flatMap {
    case ((tweetId, screenName, createdAt), it) => it.map {
      case (tweetId, (replyTweet, originalTweet)) =>

        Flock(tweetId, createdAt, screenName, replyTweet.screenName, replyTweet.pic, replyTweet.followersCount)
    }
  }

  flockReplies.saveToCassandra(ks, "replies_flock")


  // Joining
  val rtsByTweetIdByMinutePair = rtsByTweetIdByMinute.map {
    case ((tweetId, screenName, createdAt), it) => ((tweetId, createdAt), TweetStats(tweetId, createdAt, screenName, it.size))
  }

  val repliesByTweetIdByMinutePair = repliesByTweetIdByMinute.map {
    case ((tweetId, screenName, createdAt), it) => ((tweetId, createdAt), TweetStats(tweetId, createdAt, screenName, it.size))
  }

  // Implement a Join as the patch for new Spark version
  // once it is released we can just delete this part of the code
  // ref: https://github.com/staple/spark/blob/7ac0aa9a01195cfb8f6abd662b0d97513dd585ac/core/src/main/scala/org/apache/spark/rdd/PairRDDFunctions.scala#L517
  val rddCogroup = rtsByTweetIdByMinutePair.cogroup(repliesByTweetIdByMinutePair)
    val fullJoined = rddCogroup.flatMapValues {
    case (vs, Seq()) => vs.map(v => (Some(v), None))
    case (Seq(), ws) => ws.map(w => (None, Some(w)))
    case (vs, ws) => for (v <- vs; w <- ws) yield (Some(v), Some(w))
  }

  // UGLY!!! need improvement
  val tweetStatsTable = fullJoined.map {
    case ((tweetId, createdAt), (Some(TweetStats(_, _, screenName, rtCount)), Some(TweetStats(_, _, _, replyCount)))) => TweetCounter(tweetId, createdAt, screenName, rtCount, replyCount)
    case ((tweetId, createdAt), (None, Some(TweetStats(_, _, screenName, replyCount)))) => TweetCounter(tweetId, createdAt, screenName, 0, replyCount)
    case ((tweetId, createdAt), (Some(TweetStats(_, _, screenName, rtCount)), None)) => TweetCounter(tweetId, createdAt, screenName, rtCount, 0)
  }

  tweetStatsTable.saveToCassandra(ks, "tweet_stats")
}
