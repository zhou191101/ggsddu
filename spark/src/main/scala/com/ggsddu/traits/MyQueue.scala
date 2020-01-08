package com.ggsddu.traits

/**
 *
 * @author zhoup
 *
 */
class MyQueue  extends BasicIntQueue with Doubling

object MyQueue{
  def main(args: Array[String]): Unit = {
    val myQueue = new MyQueue
    myQueue.put(10)
    println(myQueue.get())
  }
}