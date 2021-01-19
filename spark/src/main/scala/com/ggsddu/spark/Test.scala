package com.ggsddu.spark

import org.apache.spark.network.protocol.Encoders
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Test {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("test")
      .getOrCreate()

    val list = List(("a,b,c,2", 1), ("a,b,c,1", 2))
    import spark.implicits._
   val r = spark.createDataFrame(list).rdd
      .map(r => {
        val r1 = r.getString(0)
        val map = new mutable.HashMap[String, String]()
        map.put("a", "b")
        Row.fromSeq(r1.split(","))

      })
    spark.createDataFrame(r,new StructType()
      .add(StructField("a",StringType))
    .add(StructField("b",StringType))
      .add(StructField("c",StringType))
      .add(StructField("d",StringType))).show()
  }
}
