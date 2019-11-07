package com.ggsddu.exception

import java.io.{BufferedReader, FileReader}

import scala.util.{Failure, Success, Try}

/**
 *
 * @author zhoup
 *
 */
object TryTest {

  def main(args: Array[String]): Unit = {
    val br: Try[BufferedReader] = Try(new BufferedReader(new FileReader("aa.txt")))
    br match {
      case Success(bufferedReader) =>
        bufferedReader.readLine()
      case Failure(exception) =>
        print(exception.getCause)
    }
  }
}
