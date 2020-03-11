package com.ggsddu.scala.batch

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

object BatchWordCount {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val input = "test.txt"

    val text = env.readTextFile(input)
    text.flatMap(_.toLowerCase().split("\\W+"))
      .map((_, 1))
      .groupBy(0)
      .sum(1)
      .print()

    // env.execute()

  }

}
