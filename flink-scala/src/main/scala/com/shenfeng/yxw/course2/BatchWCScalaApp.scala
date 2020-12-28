package com.shenfeng.yxw.course2

import org.apache.flink.api.scala.ExecutionEnvironment

/**
  * @Author yangxw
  * @Date 2020-12-28 上午11:56
  * @Description
  * @Version 1.0
  */
object BatchWCScalaApp {

  def main(args: Array[String]): Unit = {
    val input = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-scala\\src\\main\\resources\\input\\word.txt"
    val env = ExecutionEnvironment.getExecutionEnvironment

    val text = env.readTextFile(input)

    text.flatMap(_.toLowerCase.split("\t"))
      .filter(_.nonEmpty)
      .map((_, 1))
      .groupBy(0)
      .sum(1)
      .print()


  }

}
