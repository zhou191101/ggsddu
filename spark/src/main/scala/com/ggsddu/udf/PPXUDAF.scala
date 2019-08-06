package com.ggsddu.udf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

class PPXUDAF extends UserDefinedAggregateFunction {
  override def inputSchema: StructType = {
    StructType(
      StructField("app_wd", StringType)
        :: StructField("app_coder", IntegerType)
        :: StructField("coef", DoubleType)
        :: StructField("group", IntegerType)
        :: Nil
    )
  }

  override def bufferSchema: StructType = {
    StructType(
      StructField("app_wd", StringType)
        :: StructField("app_coder", IntegerType)
        :: StructField("coef", DoubleType)
        :: StructField("group", IntegerType)
        :: Nil
    )
  }

  override def dataType: DataType = new ArrayType(StringType, true)

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = null
    buffer(1) = 0
    buffer(2) = 0.0
    buffer(3) = 0
  }

  override def update(
                       buffer: MutableAggregationBuffer,
                       input: Row): Unit = {
    ???
  }

  override def merge(
                      buffer1: MutableAggregationBuffer,
                      buffer2: Row): Unit = {
    ???
  }

  override def evaluate(buffer: Row): Any = ???
}
