package com.ggsddu.scala.kafka

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010

object StreamKafkaSource{

  def main(args: Array[String]): Unit ={

    val env = StreamExecutionEnvironment.getExecutionEnvironment
   // env.setParallelism(1)
    import org.apache.flink.streaming.api.scala._

    val prop = new Properties()
    prop.setProperty("bootstrap.servers","")
    prop.setProperty("group.id","aq122")

    val data = env.addSource(new FlinkKafkaConsumer010[String]("pass_blood_p3",new SimpleStringSchema(),prop))
    data.print()
    env.execute()
  }

}
