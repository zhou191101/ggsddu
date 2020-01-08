package com.ggsddu.traits

import scala.collection.mutable.ArrayBuffer

/**
 *
 * @author zhoup
 *
 */

class BasicIntQueue extends IntQueue {

  private val buffer = new ArrayBuffer[Int]()

  override def get(): Int = buffer.remove(0)

  override def put(x: Int): Unit = buffer += x
}


object BasicIntQueue {
  def main(args: Array[String]): Unit = {
    val queue = new BasicIntQueue() with Doubling with Incrementing with Filtering
    queue.put(-1)
    queue.put(10)
    queue.put(100)
    println(queue.get())
  }
}
