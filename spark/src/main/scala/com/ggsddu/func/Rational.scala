package com.ggsddu.func

/**
 *
 * @author zhoup
 *
 */
class Rational(n: Int, d: Int) extends Ordered[Rational] {
  require(d != 0)

  private def gcd(a: Int, b: Int): Int = {
    if (b == 0) a else gcd(b, a % b)
  }

  private val g = gcd(n.abs, d.abs)
  val number = n / g
  val denom = d / g

  override def toString: String = n + "/" + d

  def this(n: Int) = this(n, 1)

  private def add(that: Rational): Rational = {
    new Rational(number * that.denom + that.number * denom, denom * that.denom)
  }

  def +(that: Rational): Rational = add(that)

  def *(that: Rational): Rational = new Rational(number * that.number, denom * that.denom)

  def *(i: Int): Rational = new Rational(number * i, denom)


  def lessThan(that: Rational) = this.number * that.denom < that.number * this.denom

  def max(that: Rational) = if (lessThan(that)) that else this

  override def compare(that: Rational): Int = {
    this.number * that.denom - that.number * this.denom
  }
}

object Rational {
  def main(args: Array[String]): Unit = {
    val oneHalf = new Rational(1, 2)
    val twoThird = new Rational(2, 3)
    println(oneHalf + twoThird)
    println(oneHalf * twoThird)
    println(oneHalf + oneHalf * twoThird)
    println(oneHalf * 2)

    implicit def intToRational(x: Int) = new Rational(x)

    println(2 * oneHalf)
    println(oneHalf < twoThird)
  }
}
