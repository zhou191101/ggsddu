package com.ggsddu.future

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

object Test extends App {

  val future = Future {
    Thread.sleep(5000)
    21 + 21
  }
  while (!future.isCompleted) {
    println(future.value)
  }
  println("final " + future.value)
}
