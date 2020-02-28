package com.ggsddu.selector

object Domain {

  def apply(parts: String*): String = parts.reverse.mkString(".")

  def unapplySeq(whole: String): Option[Seq[String]] =
    Some(whole.split("\\.").reverse)

  def main(args: Array[String]): Unit = {
    val s = "acm.org"
    s match {
      case Domain("org", "acm") => println("acm.org")
      case Domain("net", _*) => println("a .net domain")
    }
  }
}
