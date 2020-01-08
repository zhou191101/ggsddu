package com.ggsddu.traits

import scala.collection.mutable.ArrayBuffer

/**
 *
 * @author zhoup
 *
 */
abstract class IntQueue {
  def get(): Int

  def put(x: Int)
}

trait Doubling extends IntQueue {
  abstract override def put(x: Int) = {
    super.put(2 * x)
  }
}

trait Incrementing extends IntQueue {
  abstract override def put(x: Int): Unit = {
    super.put(x + 1)
  }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int): Unit = {
    if (x >= 0) super.put(x)
  }
}