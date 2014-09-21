// stop the provided SparkConf, we need to create
// another with C* driver support
sc.stop

import org.apache.spark._
import org.apache.spark.SparkContext._
import com.datastax.spark.connector._

// Configure the new Context
val conf = new SparkConf().
set("spark.cassandra.connection.host", "localhost"). // supports username/password
setAppName("Nardoz Shell"). // displayed at SparkUI
set("spark.ui.port", "4041") // just to avoid the warning message if other shell/job is running locally

// Create the new SparkContext
val sc = new SparkContext(conf)
