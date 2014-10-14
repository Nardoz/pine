package com.nardoz.pine

import scala.util.Try
import org.json4s.ParserUtil.ParseException

object Utils {

  import org.json4s._
  import org.json4s.native._
  import org.json4s.native.JsonMethods._

  implicit val formats = org.json4s.DefaultFormats

  //  val sf = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy")

  def roundDownByMinute(epoch: Long) = (epoch / 60) * 60

  def fromJsonToDataPoint(payload: String): Option[DataPoint] = {
    try {
      val json = parse(payload)

      // Main Tweet
      val tweetId = (json \ "id").extract[Long]
      val createdAt = (json \ "timestamp_ms").extract[String].toLong / 1000L // sf.parse((json \ "created_at").extract[String]).getTime / 1000L

      assert(createdAt > 0)

      val screenName = (json \ "user" \ "screen_name").extract[String].toLowerCase()
      val pic = (json \ "user" \ "profile_image_url").extract[String]
      val followersCount = (json \ "user" \ "followers_count").extract[Int]

      // ReplyId
      val inReplyToStatusId = (json \ "in_reply_to_status_id").extract[Long]

      // Retweet
      val retweetedStatusId = (json \ "retweeted_status" \ "id").toOption.getOrElse(JInt(0)).extract[Long]
      val retweetedScreenName = (json \ "retweeted_status" \ "user" \ "screen_name").toOption.getOrElse(JString("")).extract[String].toLowerCase()
      val retweetedPic = (json \ "retweeted_status" \ "user" \ "profile_image_url").toOption.getOrElse(JString("")).extract[String]
      val retweetedFollowersCount = (json \ "retweeted_status" \ "user" \ "followers_count").toOption.getOrElse(JInt(0)).extract[Int]

      Some(DataPoint(tweetId, createdAt, screenName, pic, followersCount, inReplyToStatusId, retweetedStatusId, retweetedScreenName, retweetedPic, retweetedFollowersCount))
    } catch {
      case e: ParseException => None
      case e: MappingException => None

      case e: Exception => {
        e.printStackTrace()
        None
      }
    }
  }

  // Generic Structure to extract from JSON
  case class DataPoint(
                        tweetId: Long, createdAt: Long, screenName: String, pic: String, followersCount: Int,
                        inReplyToStatusId: Long,
                        retweetedStatusId: Long, retweetedScreenName: String, retweetedPic: String, retweetedFollowersCount: Int)

  case class Tweet(tweetId: Long, createdAt: Long, screenName: String, pic: String, followersCount: Int)

  case class TweetStats(tweetId: Long, createdAt: Long, screenName: String, count: Int)

  case class TweetCounter(tweetId: Long, createdAt: Long, screenName: String, retweetCount: Int = 0, replyCount: Int = 0)


  case class Flock(tweetId: Long, createdAt: Long, screenName: String, fanScreenName: String, fanPic: String, fanFollowers: Long)

}