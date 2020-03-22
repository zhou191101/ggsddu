package com.ggsddu.scala.remote

import org.apache.flink.client.cli.CliFrontend
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

object Remote {

  def main(args: Array[String]): Unit = {
    val builder = new ProcessBuilder()
    println(builder.environment().get("FLINK_CONF_DIR"))
    //CliFrontend.main(Array[String]("run","-m yarn-cluster","-p2","-yjm 1024m", "-ytm 1024m","/Users/zhoupeng/Downloads/bigdata/flink/examples/batch/WordCount.jar"))




//    val env = StreamExecutionEnvironment.createRemoteEnvironment("10.205.22.223", 8081, "/Users/zhoupeng/Downloads/bigdata/flink/examples/batch/WordCount.jar")
//
//    val text = env.socketTextStream("localhost", 9999)
//
//    env.execute("flink word count")

  }
}
