package com.ggsddu.future

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

object Test extends App {

  val future1 = Future {
    var count = 0
    while (count < 100) {
      println(Thread.currentThread().getName + "--" + count)
      count +=1
    }
  }
  val future2 = Future {
    var count = 0
    while (count < 100) {
      println(Thread.currentThread().getName + "--" + count)
      count +=1
    }
  }

  Thread.sleep(1000)
}
