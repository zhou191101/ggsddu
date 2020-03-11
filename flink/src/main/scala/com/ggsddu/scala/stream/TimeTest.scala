package com.ggsddu.scala.stream

import java.text.SimpleDateFormat

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

object TimeTest {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(1)

    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime)
    val text = env.socketTextStream("localhost", 9999, '\n')
    import org.apache.flink.streaming.api.scala._
    val dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")
    text.flatMap(_.split("\\W+"))
      .map(x => {
        println("data:" + x + " | time:" + dfs.format(System.currentTimeMillis()))
        (x, 1)
      })
      .keyBy(0)
      .timeWindow(Time.seconds(3))
      .sum(1)
      .print()

    env.execute()


  }

}
