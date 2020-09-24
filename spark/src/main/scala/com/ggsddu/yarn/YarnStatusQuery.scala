package com.ggsddu.yarn

import scala.collection.mutable
import sys.process._

object YarnStatusQuery {

  def main(args: Array[String]): Unit = {

    //    val rs = "yarn application -appStates ALL -list".!!
    //
    //    println(rs)
    //    val applications = rs.split("\n")
    //
    //    val appResult = applications.filter(_.startsWith("application_"))
    //      .map(app => {
    //        val info = app.split("\t")
    //        val appStatus = s"yarn application -status ${info(0)}".!!.split("\n")
    //        val finalState = appStatus.filter(_.trim.startsWith("Final-State"))(0)
    //        val startTime = appStatus.filter(_.trim.startsWith("Start-Time"))(0)
    //        val finishTime = appStatus.filter(_.trim.startsWith("Finish-Time"))(0)
    //        AppResult(info(0).trim, info(1).trim, info(5).trim, finalState.split(":")(1).trim,
    //          startTime.split(":")(1).trim, finishTime.split(":")(1).trim)
    //      })

    println(queryAppInfo(Seq("wormhole_test_test4", "wormhole_test_test")))

  }

  private def queryAppInfo(appNames: Seq[String]): Map[String, AppResult] = {
    val resultMap = mutable.HashMap.empty[String, AppResult]
    val rs = "yarn application -appStates ALL -list".!!
    println(rs)
    rs.split("\n")
      .filter(app => app.startsWith("application_") && appNames.contains(app.split("\t")(1).trim))
      .foreach(app => {
        println(app)
        val info = app.split("\t")
        val appName = info(1).trim
        val appStatus = s"yarn application -status ${info(0)}".!!.split("\n")
        val url = appStatus
          .filter(_.trim.startsWith("Tracking-URL"))(0)
          .substring(15)
          .trim + "/jobs/overview"
        // appStatus.foreach(println)
        println(url)
        val finalState = appStatus.filter(_.trim.startsWith("Final-State"))(0).split(":")(1).trim
        val startTime = appStatus.filter(_.trim.startsWith("Start-Time"))(0).split(":")(1).trim
        val finishTime = appStatus.filter(_.trim.startsWith("Finish-Time"))(0).split(":")(1).trim
        if (resultMap.contains(appName)) {
          if (startTime > resultMap(appName).startedTime) {
            resultMap += appName -> AppResult(info(0).trim, appName, info(5).trim, finalState, startTime, finishTime)
          }
        } else {
          resultMap += appName -> AppResult(info(0).trim, appName, info(5).trim, finalState, startTime, finishTime)
        }
      })

    resultMap.toMap
  }

}

case class AppResult(appId: String, appName: String, appStatus: String, finalStatus: String, startedTime: String, finishedTime: String)
