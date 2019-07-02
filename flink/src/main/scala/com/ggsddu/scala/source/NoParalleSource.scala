package com.ggsddu.scala.source

import org.apache.flink.streaming.api.functions.source.SourceFunction


object NoParalleSource extends SourceFunction[Int] {
  private var isRunning = true
  private var count = 0

  /**
    *
    * 启动一个source
    * 大部分情况下都需要在此方法实现循环
    **/
  override def run(sourceContext: SourceFunction.SourceContext[Int]): Unit = {
    while (isRunning) {
      sourceContext.collect(count)
      count += 1
      if (count == 100) {
        isRunning = false
      }
      Thread.sleep(1000)
    }
  }

  override def cancel(): Unit = {

  }
}
