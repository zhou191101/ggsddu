package com.ggsddu.ziptable

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

trait Base {

  def conf(): SparkConf

  lazy val spark: SparkSession = SparkSession.builder().config(conf()).getOrCreate()

  def execute(args: Array[String]): Unit

  def writeToZipTable(fullDF: DataFrame, updateDF: DataFrame, primaryKeys: Set[String],
                      updateDate: String, keyMap: Map[String, String], write: DataFrame => Unit): Unit = {

    val fullFields = keyMap.values

    updateDF.createOrReplaceTempView("tmp_update")

    fullDF.createOrReplaceTempView("tmp_full")

    val updateSql =
      s"""
         |select ${fullFields.map(field => "tmp_full_update." + field).mkString(",")},tmp_full_update.start_date,
         |    case when ${primaryKeys.map(key => "update_zip." + key + " is not null").mkString(" and ")}
         |    then "${updateDate}"
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
         |select ${fullFields.mkString(",")},start_date,end_date from tmp_full where end_date !='99999999'
         |
         |union all
         |
         |select ${keyMap.map(map=>if(map._1.equals(map._2))map._2 else map._1 +" as " +map._2).mkString(",")},
         |    "${updateDate}" as start_date,"99999999" as end_date
         |from tmp_update
         |""".stripMargin
    val result = spark.sql(updateSql)
    write(result)
  }
}
