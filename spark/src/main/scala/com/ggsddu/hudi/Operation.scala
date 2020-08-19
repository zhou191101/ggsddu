package com.ggsddu.hudi

import org.apache.hudi.QuickstartUtils._

import scala.collection.JavaConversions._
import org.apache.spark.sql.SaveMode._
import org.apache.hudi.DataSourceReadOptions._
import org.apache.hudi.DataSourceWriteOptions._
import org.apache.hudi.config.HoodieWriteConfig._
import org.apache.spark.sql.{DataFrame, SparkSession}

object Operation {

  private val tableName = "hudi_trips_cow"
  private val basePath = "file:///Users/zhoupeng/project/ggsddu/tmp/hudi_trips_cow"

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("test")
      .master("local[4]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    val dataGen = new DataGenerator

    // insertData(spark, dataGen)

    //    select(spark)
    //update(spark, dataGen)

    //    incrementQuery(spark)

    timeQuery(spark)

  }


  private def insertData(spark: SparkSession, dataGen: DataGenerator): Unit = {
    val inserts = convertToStringList(dataGen.generateInserts(10))
    val df = spark.read.json(spark.sparkContext.parallelize(inserts, 2))
    df.write.format("hudi")
      .options(getQuickstartWriteConfigs)
      .option(PRECOMBINE_FIELD_OPT_KEY, "ts").
      option(RECORDKEY_FIELD_OPT_KEY, "uuid").
      option(PARTITIONPATH_FIELD_OPT_KEY, "partitionpath").
      option(TABLE_NAME, tableName).
      mode(Overwrite).
      save(basePath)
  }

  private def select(spark: SparkSession): Unit = {

    val tripSnapshotDF = spark
      .read
      .format("hudi")
      .load(basePath + "/*/*/*/*")

    tripSnapshotDF.createOrReplaceTempView("hudi_trips_snapshot")
    spark.sql("select fare, begin_lon, begin_lat, ts from  hudi_trips_snapshot where fare > 20.0").show()
    spark.sql("select _hoodie_commit_time, _hoodie_record_key, _hoodie_partition_path, rider, driver, fare from  hudi_trips_snapshot").show()
  }


  private def update(spark: SparkSession, dataGen: DataGenerator): Unit = {
    val updates = convertToStringList(dataGen.generateInserts(10))
    val df = spark.read.json(spark.sparkContext.parallelize(updates, 2))
    df.write.format("hudi").
      options(getQuickstartWriteConfigs).
      option(PRECOMBINE_FIELD_OPT_KEY, "ts").
      option(RECORDKEY_FIELD_OPT_KEY, "uuid").
      option(PARTITIONPATH_FIELD_OPT_KEY, "partitionpath").
      option(TABLE_NAME, tableName).
      mode(Append).
      save(basePath)
  }

  private def incrementQuery(spark: SparkSession): Unit = {
    spark.
      read.
      format("hudi").
      load(basePath + "/*/*/*/*").
      createOrReplaceTempView("hudi_trips_snapshot")

    import spark.implicits._
    val commits = spark.sql("select distinct(_hoodie_commit_time) as commitTime from  hudi_trips_snapshot order by commitTime")
      .map(k => k.getString(0)).take(50)
    val beginTime = commits(commits.length - 2) // commit time we are interested in

    // incrementally query data
    val tripsIncrementalDF = spark.read.format("hudi").
      option(QUERY_TYPE_OPT_KEY, QUERY_TYPE_INCREMENTAL_OPT_VAL).
      option(BEGIN_INSTANTTIME_OPT_KEY, beginTime).
      load(basePath)
    tripsIncrementalDF.createOrReplaceTempView("hudi_trips_incremental")

    spark.sql("select `_hoodie_commit_time`, fare, begin_lon, begin_lat, ts from  hudi_trips_incremental where fare > 20.0").show()
  }


  private def timeQuery(spark: SparkSession): Unit = {
    val beginTime = "000" // All commits > this time
    import spark.implicits._
    spark.
      read.
      format("hudi").
      load(basePath + "/*/*/*/*").
      createOrReplaceTempView("hudi_trips_snapshot")

    val commits = spark.sql("select distinct(_hoodie_commit_time) as commitTime from  hudi_trips_snapshot order by commitTime")
      .map(k => k.getString(0)).take(50)
    val endTime = commits(commits.length - 2)
    //incrementally query data
    val tripsPointInTimeDF = spark.read.format("hudi").
      option(QUERY_TYPE_OPT_KEY, QUERY_TYPE_INCREMENTAL_OPT_VAL).
      option(BEGIN_INSTANTTIME_OPT_KEY, beginTime).
      option(END_INSTANTTIME_OPT_KEY, endTime).
      load(basePath)
    tripsPointInTimeDF.createOrReplaceTempView("hudi_trips_point_in_time")
    spark.sql("select `_hoodie_commit_time`, fare, begin_lon, begin_lat, ts from hudi_trips_point_in_time where fare > 20.0").show()
  }
}
