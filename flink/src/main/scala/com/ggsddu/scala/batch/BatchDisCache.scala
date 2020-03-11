package com.ggsddu.scala.batch

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

object BatchDisCache {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    // hdfs:// or s3://
    env.registerCachedFile("test.txt", "test2.txt");

    val data = env.fromElements("a", "b", "c", "d")

    data.map(new RichMapFunction[String, String] {

      private val list = new ListBuffer[String]()

      override def open(parameters: Configuration): Unit = {
        super.open(parameters)
        val file = getRuntimeContext.getDistributedCache.getFile("test2.txt")
        val lines = FileUtils.readLines(file)
        for (line <- lines.asScala) {
          this.list.append(line)
          println("line: " + line)
        }
      }

      override def map(in: String): String = {
        in
      }
    }).print()


  }

}
