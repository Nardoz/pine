
package com.nardoz.pine

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import com.nardoz.pine.Utils._

object TweetsDriver extends App {

  if (args.length != 1) {
    println("Usage: TweetsDriver <filename>")
    sys.exit(1)
  }

  //  val filename = "../data/tweets.json.gz"
  val filename = args(0)

  val conf = new SparkConf()
  conf.setAppName("Nardoz.Pine TweetsDriver")
  conf.set("spark.cassandra.connection.host", "localhost")
  val sc = new SparkContext(conf)

  val jsons = sc.textFile(filename)
  val data = jsons.flatMap(fromJsonToDataPoint)

  val tweets = data.map { d =>
    (d.tweetId, Tweet(d.tweetId, ((d.createdAt / 60) * 60), d.screenName, d.followersCount))
  }

  val rts = data.
    filter(d => d.retweetedStatusId > 0). // only those with RTs
    map { d =>
      (d.retweetedStatusId, Tweet(d.tweetId, ((d.createdAt / 60) * 60), d.screenName, d.followersCount))
    }

  val tweetsWithRTs = rts.join(tweets)
  
  tweetsWithRTs.
  
  

}

