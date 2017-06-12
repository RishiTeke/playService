package controllers

import org.apache.spark.sql.DataFrame
import javax.inject._
import play.api._
import play.api.mvc._
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS

@Singleton
class Recommend @Inject() extends Controller {
  /* 
    
  */  
    val  USER_ID = 0
    val SEED = 100
    val BEST_RANK = 5
    val BEST_ITERATION = 15
    val BEST_REGULATION = 0.100000
    
   
 
  def toJsonString(rdd:DataFrame):String =
    "["+rdd.toJSON.take(5).toList.mkString(",\n")+"]"
    
    /* creating dtaframe from two .csv files */
    val accomdDF = SparkCommon.spark.read.option("header","true").option("inferSchema", "true").csv("raw_data/accommodations.csv")
    val ratingsDF = SparkCommon.spark.read.option("header","true").option("inferSchema", "true").csv("raw_data/ratings.csv")
   
   
   val als = new ALS().setMaxIter(BEST_ITERATION)
                    .setRegParam(BEST_REGULATION)
                    .setRank(BEST_RANK)
                    .setUserCol("userId")
                    .setItemCol("accoId")
                    .setRatingCol("rating")
   
    
val splits = ratingsDF.randomSplit(Array(6.0,2.0,2.0))
       val trainingDF = splits(0).cache()
       val validationDF = splits(1)
       val testDF = splits(2)
   
   val model_ml = als.fit(trainingDF)   
  
  
 
 
  //def dataframe = Action { Ok(toJsonString(FiveRecommendation))  }
  
  def userRecomend(user: Int) : DataFrame = {
      
       val dfUserRatings  = ratingsDF.select(ratingsDF("accoId")).filter(ratingsDF("userId") === user)
  val Potential = accomdDF.join(dfUserRatings, accomdDF("id")===dfUserRatings("accoId") ,"leftanti")
 val Potentialdf = Potential.select(Potential("id").alias("accoId").cast(IntegerType)).withColumn("userId", lit(user))
   
  
   val predictions = model_ml.transform(Potentialdf)
    
   val recommendation = predictions.join(accomdDF, accomdDF("id") === predictions("accoId"), "inner").orderBy("prediction")
   val Recommendation_null = recommendation.na.drop()
    return Recommendation_null.orderBy(recommendation("prediction").desc)
      
      
  }
  
 
def recomend_user(userID:Int) = Action {
    val temp = userRecomend(userID)
    Ok(toJsonString(temp))
  } 
  
  
  

}




































