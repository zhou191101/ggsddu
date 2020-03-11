package com.ggsddu.scala.stream

import com.ggsddu.java.partition.MyPartition
import com.ggsddu.scala.source.NoParalleSource
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

object StreamWithMyPartition {

  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val text = env.addSource(NoParalleSource)

    text.map((_, 1)).partitionCustom(new MyPartition(), 0).map(x => {
      println("当前线程ID：" + Thread.currentThread().getId + ",value: " + x)
      x
    }).print()

    env.execute()

  }
}
