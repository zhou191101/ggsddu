package com.ggsddu.scala.batch

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import scala.collection.mutable

object BatchJoin {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment
    val list1 = new mutable.ArrayBuffer[(Int, String)]()
    list1 += ((1, "bj"))
    list1 += ((2, "sh"))
    list1 += ((3, "gz"))
    val list2 = new mutable.ArrayBuffer[(Int, String)]()
    list2 += ((1, "beijing"))
    list2 += ((2, "shanghai"))
    list2 += ((4, "guangzhou"))
    list2 += ((4, "shenzhen"))
    /*   env.fromCollection(list1).join(env.fromCollection(list2))
         .where(0)
         .equalTo(0)
         .map(x => {
           (x._1._1, x._1._2, x._2._2)
         })
         .print()*/

    env.fromCollection(list1).leftOuterJoin(env.fromCollection(list2))
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, "null")
        } else {
          (first._1, first._2, second._2)
        }
      }).print()

  }

}
