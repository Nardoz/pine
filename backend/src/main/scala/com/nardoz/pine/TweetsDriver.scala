
package com.nardoz.pine

import org.apache.spark._
import org.apache.spark.SparkContext._
import com.datastax.spark.connector._
import com.nardoz.pine.Utils._

object TweetsDriver extends App {

  if (args.length != 1) {
    println("Usage: TweetsDriver <filename>")
    sys.exit(1)
  }

  val ks = "nardoz_pine"

  val conf = new SparkConf()
  conf.setAppName("Nardoz.Pine TweetsDriver")
  conf.set("spark.cassandra.connection.host", "localhost")
  val sc = new SparkContext(conf)

  val filename = args(0)
  //  val filename = "../data/tweets.json.gz"
  val jsons = sc.textFile(filename)
  val data = jsons.flatMap(fromJsonToDataPoint)

  val tweets = data.map { d =>
    (d.tweetId, Tweet(d.tweetId, ((d.createdAt / 60) * 60), d.screenName, d.pic, d.followersCount))
  }

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

  rtsByTweetIdByMinuteCount.saveToCassandra(ks, "tweet_stats")

  val flockTable = rtsByTweetIdByMinute.flatMap {
    case ((tweetId, screenName, createdAt), it) => it.map {
      case (tweetId, (rtTweet, originalTweet)) =>

        Flock(tweetId, createdAt, screenName, rtTweet.screenName, rtTweet.pic, rtTweet.followersCount)
    }
  }

  flockTable.saveToCassandra(ks, "flock")
}

