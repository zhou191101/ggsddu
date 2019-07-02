package com.ggsddu.scala.util

import java.text.SimpleDateFormat

object GetTimeStamp{

  def main(args: Array[String]): Unit ={

    val dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")


    while (true){

      val current = System.currentTimeMillis()
      println("0001,"+current +"," +dfs.format(current))
      Thread.sleep(1000)
    }


  }

}
