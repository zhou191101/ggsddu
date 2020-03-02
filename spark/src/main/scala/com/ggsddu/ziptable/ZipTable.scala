package com.ggsddu.ziptable

object ZipTable extends Base {


  override def execute(): Unit = {
    val df = spark.read.format("csv")
      .option("header", true)
      .option("delimiter","\t")
      .load("/Users/zhoupeng/Desktop/spark-zip-test.csv")
    df.show()

  }

  def main(args: Array[String]): Unit = {
    conf.setAppName("zip_table")
    conf.setMaster("local[*]")
    execute()
  }
}
