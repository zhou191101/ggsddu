package com.ggsddu.selector

object Email {
  def apply(user: String, domain: String): String = user + "@" + domain

  def unapply(arg: String): Option[(String, String)] = {
    val parts = arg split "@"
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }

  def main(args: Array[String]): Unit = {
    val s = "zhoupeng@tw.com"
    s match {
      case Email(user, domain) =>
        println(user + "--" + domain)
      case _ =>
        println("aaa")
    }
  }
}
