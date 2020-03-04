package com.ggsddu.ziptable

import org.apache.spark.SparkConf
import org.apache.spark.sql.DataFrame

import scala.util.parsing.json.JSON

trait Type

case class CSVType(path: String, header: Boolean = true, sep: String = ",") extends Type

case class TableType(tableName: String, partitions: Option[Map[String, String]]) extends Type

object ZipTable extends Base {

  def main(args: Array[String]): Unit = {
    val conf: String =
      s"""
         |{
         |  "incr_source":"/Users/zhoupeng/Desktop/hott-20200302",
         |  "incr_source_partitions":{"day":"202000302"},
         |  "incr_source_type":"csv",
         |  "incr_source_param":{"header":true,"sep":"\t"},
         |  "full_source":"/Users/zhoupeng/Desktop/full-hott",
         |  "full_source_partitions":{"day":"202000302"},
         |  "full_source_type":"csv",
         |  "full_source_param":{"header":true},
         |  "target":"/Users/zhoupeng/Desktop/full-hott2",
         |  "target_partitions":{"day":"202000302"},
         |  "target_type":"csv",
         |  "target_param":{"header":true},
         |  "primary_keys":["combine", "sold_to"],
         |  "update_day":"202000304",
         |  "key_map":{
         |              "combine":"combine",
         |              "primary_sales_rep_name":"primary_sales_rep_name",
         |              "sold_to_name":"sold_to_name",
         |              "sold_to":"sold_to",
         |              "ship_to":"ship_to",
         |              "plant":"plant",
         |              "sales_document_type":"sales_document_type",
         |              "created_by":"created_by",
         |              "created_on_date":"created_on_date",
         |              "contract_number":"contract_number",
         |              "overall_health_status":"overall_health_status"
         |              }
         |}
         |""".stripMargin


    execute(Array(conf))
  }


  override def execute(args: Array[String]): Unit = {

    val parseResults = parseParam(args(0))
    val fullSource = getType(parseResults, "full_source")
    val incrSource = getType(parseResults, "incr_source")
    val target = getType(parseResults, "target")

    val fullDF = getDataFrame(fullSource)
    val incrDF = getDataFrame(incrSource)
    val zipParam = getZipParam(parseResults)


    target match {
      case CSVType(path, header, sep) =>
        writeToZipTable(fullDF, incrDF, zipParam._1, zipParam._2, zipParam._3,
          df => df.write.mode("overwrite")
            .format("csv").option("header", value = header)
            .save(path)
        )
      case TableType(tableName, partitions) =>
        val partitionFileds = partitions.get.keys.toArray
        writeToZipTable(fullDF, incrDF, zipParam._1, zipParam._2, zipParam._3,
          df => df.write.mode("overwrite")
            .partitionBy(partitionFileds: _*)
            .saveAsTable(tableName))
    }
  }

  override def conf(): SparkConf = {
    new SparkConf().setAppName("zip_table").setMaster("local[1]")
  }

  private def parseParam(jsonString: String): Option[Map[String, Any]] = {
    JSON.parseFull(jsonString) match {
      case Some(map: Map[String, Any]) => Some(map)
      case None => None
    }
  }

  private def getType(result: Option[Any], pref: String): Type = result match {
    case Some(map: Map[String, Any]) =>
      val sourceType = map(pref + "_type")
      sourceType match {
        case "csv" =>
          val path = map(pref).asInstanceOf[String]
          val params = map(pref + "_param").asInstanceOf[Map[String, Any]]
          val header: Boolean = params.getOrElse("header", false).asInstanceOf[Boolean]
          val sep: String = params.getOrElse("sep", ",").asInstanceOf[String]
          CSVType(path, header, sep)
        case "table" =>
          val tableName = map(pref).asInstanceOf[String]
          val partitions = map.getOrElse(pref + "_partitions", Map.empty[String, String])
            .asInstanceOf[Map[String, String]]
          TableType(tableName, Some(partitions))
      }

    case None => throw new RuntimeException("Type is null")
  }


  private def getZipParam(result: Option[Any]): (Set[String], String, Map[String, String]) = result match {
    case Some(map: Map[String, Any]) =>
      val primaryKeys = map("primary_keys").asInstanceOf[Seq[String]]
      val updateDay = map("update_day").asInstanceOf[String]
      val keyMap = map("key_map").asInstanceOf[Map[String, String]]
      (primaryKeys.toSet, updateDay, keyMap)
    case None => throw new RuntimeException("No such key error!")
  }

  private def readFromTable(tableName: String, partitions: Option[Map[String, String]]): DataFrame = {
    //TODO:方法还未完善
    spark.read.table(tableName)
  }

  private def getDataFrame(`type`: Type): DataFrame = {
    `type` match {
      case CSVType(path, header, sep) =>
        readFromCSV(path, header, sep)
      case TableType(tableName, partitions) =>
        readFromTable(tableName, partitions)
    }
  }


  private def readFromCSV(path: String, header: Boolean = true, sep: String = ","): DataFrame = {
    spark.read.format("csv")
      .option("header", value = header)
      .option("delimiter", sep)
      .load(path)
  }


}
