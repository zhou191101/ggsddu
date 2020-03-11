package com.ggsddu.scala.stream


import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time


object SocketMerge {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    env.setParallelism(1)
    import org.apache.flink.streaming.api.scala._
    env.socketTextStream("localhost", 9999)
      .map(x => {
        val lines = x.split(",")
        (lines(0), Set(lines(1)))
      })
      .keyBy(0)
      .timeWindow(Time.seconds(20))
      .reduce((a, b) => {
        (a._1, a._2 ++ b._2)
      })
      .print()


    env.execute()
  }

}
