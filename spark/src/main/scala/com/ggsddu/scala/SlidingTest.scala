package com.ggsddu.scala

object SlidingTest {

  def main(args: Array[String]): Unit = {


    val list = List("a", "b", "c", "d")
    val list2 = List("b", "c")
    println(list.diff(list2))
  }
}
