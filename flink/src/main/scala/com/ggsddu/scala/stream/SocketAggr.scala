package com.ggsddu.scala.stream

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

object SocketAggr {
  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)


    val input = env.socketTextStream("localhost", 9999, '\n')

    input.map(x => {
      (1, x.toInt)
    })

  }

}
