package flinktrain.course4

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
  * @Author yangxw
  * @Date 2020-12-30 下午8:07
  * @Description
  * @Version 1.0
  */
object DistributedCacheApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment
    val filePath = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\distributedCache\\cc.text"

    // step1: 注册一个本地文件
    env.registerCachedFile(filePath, "pk-scala-dc")

    val data = env.fromElements("hadoop", "spark", "flink", "storm")

    data.map(new RichMapFunction[String, String] {
      override def open(parameters: Configuration): Unit = {
        // step2: 在open方法中获取到分布式缓存的内容即可
        val dcFile = getRuntimeContext.getDistributedCache().getFile("pk-scala-dc")

        /**
          * java的集合和scala集合不兼容
          */

        val lines = FileUtils.readLines(dcFile)
        import scala.collection.JavaConverters._
        for (ele <- lines.asScala) {
          println(ele)
        }
      }

      override def map(in: String): String = {
        in
      }
    }).print()
  }

}
