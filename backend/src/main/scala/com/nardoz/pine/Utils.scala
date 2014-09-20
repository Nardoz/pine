package com.nardoz.pine

import scala.util.Try

object Utils {
  import org.json4s._
  import org.json4s.native._
  import org.json4s.native.JsonMethods._
  implicit val formats = org.json4s.DefaultFormats

  val sf = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy")

  def fromJsonToDataPoint(payload: String): Option[DataPoint] = {
    Try {
      val json = parse(payload)

      // Main Tweet
      val tweetId = (json \ "id").extract[Long]
      val createdAt = sf.parse((json \ "created_at").extract[String]).getTime / 1000L
      val screenName = (json \ "user" \ "screen_name").extract[String]
      val followersCount = (json \ "user" \ "followers_count").extract[Int]

      // ReplyId
      val inReplyToStatusId = (json \ "in_reply_to_status_id").extract[Long]

      // Retweet
      val retweetedStatusId = (json \ "retweeted_status" \ "id").toOption.getOrElse(JInt(0)).extract[Long]
      val retweetedScreenName = (json \ "retweeted_status" \ "user" \ "screen_name").toOption.getOrElse(JString("")).extract[String]
      val retweetedFollowersCount = (json \ "retweeted_status" \ "user" \ "followers_count").toOption.getOrElse(JInt(0)).extract[Int]

      DataPoint(tweetId, createdAt, screenName, followersCount, inReplyToStatusId, retweetedStatusId, retweetedScreenName, retweetedFollowersCount)
    }.toOption
  }

  // Generic Structure to extract from JSON
  case class DataPoint(tweetId: Long, createdAt: Long, screenName: String, followersCount: Int, inReplyToStatusId: Long, retweetedStatusId: Long, retweetedScreenName: String, retweetedFollowersCount: Int)
  
  case class Tweet(tweetId: Long, createdAt: Long, screenName: String, followersCount: Int)
  

}