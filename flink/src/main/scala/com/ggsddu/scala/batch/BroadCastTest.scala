package com.ggsddu.scala.batch

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import org.apache.flink.api.scala._

object BroadCastTest {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val buffer = new mutable.ArrayBuffer[(String, Int)]()
    buffer += (("zhangsan", 18))
    buffer += (("lisi", 17))
    buffer += (("wangwu", 20))
    buffer += (("laozhou", 25))

    val data = env.fromCollection(buffer).map(x => {
      Map(x._1 -> x._2)
    })

   // data.print()
    val metaData = env.fromElements("zs", "ls", "ww")

    metaData.map(new RichMapFunction[String, String] {
      var list: java.util.List[mutable.Map[String, Int]] = null
      val map = new mutable.HashMap[String, Int]()

      // 这个方法只执行一次，可以在这里实现一些初始化功能，所有，就可以早open方法中获取广播变量
      override def open(parameters: Configuration): Unit = {
        list = getRuntimeContext.getBroadcastVariable[mutable.Map[String, Int]]("broadcast")
        val iter = list.iterator()
        while (iter.hasNext){
          map .++(iter.next())
        }

      }

      override def map(in: String): String = {

        in + "--" + map.apply(in)
      }
    }).withBroadcastSet(data, "broadcast").print()
  }
}
