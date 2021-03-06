
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
    (d.tweetId, Tweet(d.tweetId, ((d.createdAt / 60) * 60), d.screenName, d.pic, d.followersCount))
  }

  // --------------------------------
  // ReTweet logic

  val rts = data.
    filter(d => d.retweetedStatusId > 0). // only those with RTs
    map { d =>

      (d.retweetedStatusId, // Tweet Original 
        Tweet(d.tweetId, ((d.createdAt / 60) * 60), d.screenName, d.pic, d.followersCount) // this is the ReTweet
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
    (d.inReplyToStatusId, Tweet(d.tweetId, ((d.createdAt / 60) * 60), d.screenName, d.pic, d.followersCount))
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

}

