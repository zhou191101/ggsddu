package com.ggsddu.scala


import scala.collection.mutable.ListBuffer
import scala.util.Random

object RandomTest {

  def main(args: Array[String]): Unit = {

    val list = new ListBuffer[Point]
    for (i <- 0 until 150000) {
      val point = new Point
      point.cluser = i
      list.append(point)
    }

    val rs = Random.shuffle(list).take(4000)

    println(rs(3999))

  }

}


class Point {
  val lat = 0.0
  val lon = 0.0
  val geohash6 = ""
  val geoHashAdjacent = Array[String]("a", "b", "c", "D")
  val centerLon = 180
  val centerLat = 90
  val index = 1
  var cluser = 0
  val visited = 1


  override def toString = s"Point($lat, $lon, $geohash6, $geoHashAdjacent, $centerLon, $centerLat, $index, $cluser, $visited)"
}
