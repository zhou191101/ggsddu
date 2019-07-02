package com.ggsddu.scala.source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

object StreamDemoWithNoParalle {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.addSource(NoParalleSource).map(x => {
      println("value:" + x)
      x
    }).timeWindowAll(Time.seconds(2))
      .sum(0).print()
    env.execute()
  }
}
