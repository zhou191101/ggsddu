package com.ggsddu.ziptable

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

trait Base {

  def conf(): SparkConf

  lazy val spark: SparkSession = SparkSession.builder().config(conf()).getOrCreate()

  def execute(args: Array[String]): Unit

  def writeToZipTable(fullDF: DataFrame, updateDF: DataFrame, primaryKeys: Set[String],
                      endDay: String, write: DataFrame => Unit): Unit = {

    val fullFields = fullDF.schema.fields.map(_.name).toList

    updateDF.createOrReplaceTempView("tmp_update")

    fullDF.createOrReplaceTempView("tmp_full")

    val updateSql =
      s"""
         |select ${fullFields.filter(!_.equals("end_date")).map(field => "tmp_full_update." + field).mkString(",")},
         |    case when ${primaryKeys.map(key => "update_zip." + key + " is not null").mkString(" and ")}
         |    then "${endDay}"
         |    else tmp_full_update.end_date
         |    end as end_date
         |from
         |(select * from tmp_full where end_date='99999999')tmp_full_update
         |left join
         |(select ${primaryKeys.mkString(",")} from tmp_update) update_zip
         |on ${primaryKeys.map(key => "tmp_full_update." + key + "=" + "update_zip." + key).mkString(" and ")}
         |
         |union all
         |
         |select ${fullFields.mkString(",")} from tmp_full where end_date !='99999999'
         |
         |union all
         |
         |select ${fullFields.filter(x => !Set("start_date", "end_date").contains(x)).mkString(",")},
         |    "${endDay}" as start_date,"99999999" as end_date
         |from tmp_update
         |""".stripMargin
    val result = spark.sql(updateSql)
    write(result)
  }
}
