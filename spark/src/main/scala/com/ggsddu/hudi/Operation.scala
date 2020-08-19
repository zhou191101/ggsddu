package com.ggsddu.hudi

import org.apache.hudi.QuickstartUtils._
import scala.collection.JavaConversions._
import org.apache.spark.sql.SaveMode._
import org.apache.hudi.DataSourceReadOptions._
import org.apache.hudi.DataSourceWriteOptions._
import org.apache.hudi.config.HoodieWriteConfig._
import org.apache.spark.sql.SparkSession

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

    insertData(spark, dataGen)

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

}
