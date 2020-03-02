package com.ggsddu.ziptable

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait Base {
  val conf = new SparkConf()
  lazy val spark = SparkSession.builder().config(conf).getOrCreate()

  def execute(): Unit
}
