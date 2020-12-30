package flinktrain.course4

import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode

/**
  * @Author yangxw
  * @Date 2020-12-30 下午7:26
  * @Description
  * @Version 1.0
  */
object CounterApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = env.fromElements("hadoop", "spark", "storm", "hbase").setParallelism(4)

    //    data.map(new RichMapFunction[String, Long] {
    //      var count = 0L
    //
    //      override def map(in: String): Long = {
    //        count = count + 1
    //        println("counter: " + count)
    //        count
    //      }
    //    }).print()


    data.map(new RichMapFunction[String, String] {
      // step1: 定义计数器
      var counter = new LongCounter()

      override def open(parameters: Configuration): Unit = {
        // step2: 注册计数器
        getRuntimeContext.addAccumulator("ele-counts-scala", counter)
      }

      override def map(in: String): String = {
        counter.add(1)
        in
      }
    }).print()

    val filePath = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\outputCount\\"
    data.writeAsText(filePath, WriteMode.OVERWRITE)


    val jobRes = env.execute("CounterApp")
    val res = jobRes.getAccumulatorResult[Long]("ele-counts-scala")
    println("res: " + res)
  }

}
