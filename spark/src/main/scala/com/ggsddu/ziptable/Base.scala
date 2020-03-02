package com.ggsddu.ziptable

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

trait Base {

  def conf(): SparkConf

  lazy val spark: SparkSession = SparkSession.builder().config(conf()).getOrCreate()

  def execute(args: Array[String]): Unit

  def writeToZipTable(fullDF: DataFrame, updateDF: DataFrame, primaryKeys: Set[String],
                      endDay: String, f: DataFrame => Unit): Unit = {

    val updateFields = updateDF.schema.fields.map(_.name).toList
    val fullFields = fullDF.schema.fields.map(_.name).toList

    updateDF.createOrReplaceTempView("tmp_update")

    fullDF.createOrReplaceTempView("tmp_full")

    val updateSql =
      s"""
         |select ${fullFields.filter(!_.equals("end_date")).map(field => "tmp_full." + field).mkString(",")},
         |    case when ${primaryKeys.map(key => "update_zip." + key + " is not null").mkString(" and ")}
         |    then "${endDay}"
         |    else tmp_full.end_date
         |    end as end_date
         |from tmp_full
         |left join
         |(select ${primaryKeys.mkString(",")} from tmp_update) update_zip
         |on ${primaryKeys.map(key => "tmp_full." + key + "=" + "update_zip." + key).mkString(" and ")}
         |union all
         |select ${updateFields.mkString(",")}, "${endDay}" as start_date,"99999999" as end_date
         |from tmp_update
         |""".stripMargin
    val result = spark.sql(updateSql)
    f(result)
  }
}
