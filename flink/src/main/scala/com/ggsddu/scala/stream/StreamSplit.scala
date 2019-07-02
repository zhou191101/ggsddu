package com.ggsddu.scala.stream

import com.ggsddu.scala.source.NoParalleSource
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

import scala.collection.mutable
object StreamSplit {

  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val text  =env.addSource(NoParalleSource).setParallelism(1)
    text.split(x=>{
      val buffer  = new mutable.ArrayBuffer[String]()
      if(x%2==0){
        buffer += "aa"
      }else{
        buffer += "bb"
      }
      buffer
    }).select("aa").rebalance
    env.execute()
  }
}
