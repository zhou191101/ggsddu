package com.ggsddu.udf

import org.apache.spark.sql.api.java.UDF1

class PPXUDF extends UDF1[Seq[(Double, Int)], (Double, Double)] {

  override def call(t1: Seq[(Double, Int)]): (Double, Double) = {

    if (t1 == null || t1.isEmpty) {
      (0.825234800758695, 0.5)
    } else {
      var appScore = t1.groupBy(_._2).mapValues(_.map(_._1).sum)

      if (!appScore.keySet.contains(1)) {
        appScore += (1 -> 0)

      } else if (!appScore.keySet.contains(2)) {
        appScore += (2 -> 0)
      }

      val result = appScore.map(map => {
        val key = map._1
        val value = map._2
        key match {
          case 1 => (key, 1 / (1 + Math.exp(value + -1.55222459843)))
          case _ => (key, 1 / (1 + Math.exp(value + 0)))
        }
      }).toList.sortBy(_._1)
      (result(0)._2, result(1)._2)
    }

  }
}

object PPXUDF {
  def main(args: Array[String]): Unit = {
    val test = new PPXUDF
    val res = test.call(List((-0.12, 1), (-0.13, 2), (0.123, 2)))
    println(res)
  }
}