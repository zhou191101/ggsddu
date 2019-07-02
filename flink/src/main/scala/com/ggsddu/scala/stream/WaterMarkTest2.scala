package com.ggsddu.scala.stream

import java.text.SimpleDateFormat

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.{AssignerWithPeriodicWatermarks, AssignerWithPunctuatedWatermarks}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ArrayBuffer

object WaterMarkTest2{

  def main(args: Array[String]): Unit ={


    val env = StreamExecutionEnvironment.getExecutionEnvironment
    import org.apache.flink.streaming.api.scala._

    //val outputTag = new OutputTag[(String,Long)]("late-data")
    //  设置使用EventTime 默认使用的是ProcessTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 设置并行度为1 如果不设置则默认为cpu的核数
    env.setParallelism(1)
    // 从socket中获取数据
    val input = env.socketTextStream("localhost", 9999, '\n')


    val result = input.map(data => {
      val datas = data.split(",")
      (datas(0), datas(1).toLong)
    }).assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks[(String, Long)]{
      override def checkAndGetNextWatermark(lastElement: (String, Long), extractedTimestamp: Long): Watermark ={
        if(lastElement._2 == 1561719428344L) new Watermark(extractedTimestamp) else null
      }

      override def extractTimestamp(element: (String, Long), previousElementTimestamp: Long): Long ={
        element._2
      }
    }).keyBy(0).window(TumblingEventTimeWindows.of(Time.seconds(1)))
      .apply(new WindowFunction[(String, Long), String, Tuple, TimeWindow]{
        override def apply(key: Tuple, window: TimeWindow, input: Iterable[(String, Long)],
                           out: Collector[String]): Unit ={
          val list = new ArrayBuffer[Long]
          input.foreach(it => {
            list.append(it._2)
          })


          val dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")
          val max = list.max
          val min = list.min

          out
            .collect(key + ",sum: " + list.length + ",min: " + min + "|" + dfs.format(min) + ",max: " + max + "|" +
              dfs.format(max))
        }
      })
    result.print()

    env.execute()
  }

}
