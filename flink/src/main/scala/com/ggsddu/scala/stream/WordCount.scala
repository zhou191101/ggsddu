package com.ggsddu.scala.stream

import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._

object WordCount {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.enableCheckpointing(1000)

    env.setStateBackend(new RocksDBStateBackend(""))


    val text = env.socketTextStream("localhost", 9999)

    val count = text.flatMap(_.toLowerCase().split("\\W+")).filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(2),Time.seconds(1))
      .sum(1)
    count.print()

    env.execute("flink word count")
  }
}
