package com.shenfeng.yxw.course3

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @Author yangxw
  * @Date 2020-12-29 下午3:51
  * @Description
  * @Version 1.0
  */
object StreamingWCScalaApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val text = env.socketTextStream("localhost", 9999)

    text.flatMap(_.split(","))
      .map(x => WC(x.toLowerCase(), 1))
      .keyBy("word")
      .timeWindow(Time.seconds(5))
      .sum("count")
      .print()
      .setParallelism(1)
    env.execute("StreamingWCScalaApp")
  }

  case class WC(word: String, count: Int)

}
