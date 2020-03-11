package com.ggsddu.scala.stream

import java.text.SimpleDateFormat
import java.util
import java.util.Collections

import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object WaterMarkTest {

  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment
    import org.apache.flink.streaming.api.scala._

    val outputTag = new OutputTag[(String, Long)]("late-data")
    //  设置使用EventTime 默认使用的是ProcessTime
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 设置并行度为1 如果不设置则默认为cpu的核数
    env.setParallelism(3)
    // 从socket中获取数据
    val input = env.socketTextStream("localhost", 9999, '\n')


    val result = input.map(data => {
      val datas = data.split(",")
      (datas(0), datas(1).toLong)
    }).assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(String, Long)] { // 抽取timestamp为watermark
      // 最大允许乱序时间是10s
      val maxOutOfOrders = 10000L
      var currentTimestamp = 0L
      val dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")

      // 定义生成watermark的逻辑，默认100ms被调用一次
      override def getCurrentWatermark: Watermark = {

        new Watermark(currentTimestamp - maxOutOfOrders)
      }

      // 定义如何提取timestamp
      override def extractTimestamp(element: (String, Long), previousElementTimestamp: Long): Long = {
        val timestamp = element._2
        val id = Thread.currentThread().getId
        currentTimestamp = Math.max(currentTimestamp, timestamp)
        println("thread: " + id + ",key: " + element._1 + ",EventTime:[ " + element._2 + "|" + dfs.format(element._2) +
          " ],currentTimestamp:[ " + dfs.format(currentTimestamp) + " ],watermark:[ " +
          dfs.format(getCurrentWatermark.getTimestamp) + " ]")
        timestamp
      }
    }).keyBy(0)
      // 按照消息的EventTime分配窗口，和调用TimeWindow效果一样
      .window(TumblingEventTimeWindows.of(Time.seconds(3)))
      //.allowedLateness(Time.seconds(2)) // 允许最大延迟
      // 收集迟到的数据
      .sideOutputLateData(outputTag)
      .apply(new WindowFunction[(String, Long), String, Tuple, TimeWindow] {
        override def apply(key: Tuple, window: TimeWindow, input: Iterable[(String, Long)],
                           out: Collector[String]): Unit = {
          val list = new ArrayBuffer[Long]
          input.foreach(it => {
            list.append(it._2)
          })


          val dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")
          // val result = key + "," + sortList.length + "," + dfs.format(sortList(0)) + "," +
          //   dfs.format(sortList.length - 1) + "," + dfs.format(window.getStart) + "," + dfs.format(window.getEnd)
          val max = list.max
          val min = list.min

          out.collect(key + ",sum: " + list.length + ",min: " + min + "|" + dfs.format(min) + ",max: " + max + "|" +
            dfs.format(max))
          //out.collect(result)
        }
      })
    result.print()

    result.getSideOutput(outputTag).print()
    env.execute()
  }

}
