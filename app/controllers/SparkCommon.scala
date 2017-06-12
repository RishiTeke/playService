package controllers
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
 import org.apache.spark.sql.SparkSession 

//System.setProperty("hadoop.home.dir", "c:\winutil\")
/**
  * Created by ved on 21/11/15.
  */
  
  
object SparkCommon {
 
 /* lazy val conf = {
    new SparkConf(false)
      .setMaster("local[*]")
      .setAppName("Spark Tutorial")
  }

lazy val sparkContext = new SparkContext(conf)*/
//lazy val spark = SparkSession.builder().appName("SparkSession").master("spark://ubuntu-spark:7077").getOrCreate()
lazy val spark = SparkSession.builder().appName("SparkSession").master("local[*]").getOrCreate()
 
  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._
  
  //lazy val sparkSQLContext = SQLContext.getOrCreate(sparkContext)
 // import sparkSQLContext.implicits._
    

}