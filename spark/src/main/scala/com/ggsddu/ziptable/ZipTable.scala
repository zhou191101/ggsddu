package com.ggsddu.ziptable

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Column, DataFrame}

object ZipTable extends Base {

  def main(args: Array[String]): Unit = {
    execute(args)
  }

  override def execute(args: Array[String]): Unit = {
    val updateDf = readFromCSV("/Users/zhoupeng/Desktop/hott-20200302", header = true, "\t")
    val fullDf = readFromCSV("/Users/zhoupeng/Desktop/full-hott", header = true, ",")
    val endDay = "20200302"
    writeToZipTable(fullDf, updateDf, Set("combine", "sold_to"), endDay,
      df => df.write.mode("overwrite")
        .format("csv").option("header", value = true)
        .save("/Users/zhoupeng/Desktop/full-hott2")
    )

  }

  override def conf(): SparkConf = {
    new SparkConf().setAppName("zip_table").setMaster("local[1]")
  }

  def readFromCSV(path: String, header: Boolean, sep: String): DataFrame = {
    spark.read.format("csv")
      .option("header", value = header)
      .option("delimiter", sep)
      .load(path)
  }



}
