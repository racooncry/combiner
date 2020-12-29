package com.shenfeng.yxw.course2

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._

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

    text.flatMap(_.split(",")).map((_, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()
      .setParallelism(1)
    env.execute("StreamingWCScalaApp")
  }

}
