package com.ggsddu.queens


object Test {
  def main(args: Array[String]): Unit = {
    println(fibFrom(1,1).take(7).toList)
  }

  def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a + b)
}
