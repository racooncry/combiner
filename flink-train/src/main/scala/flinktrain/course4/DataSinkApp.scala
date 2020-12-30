package flinktrain.course4


import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

/**
  * @Author yangxw
  * @Date 2020-12-30 下午7:15
  * @Description
  * @Version 1.0
  */
object DataSinkApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val data = 1 to (11)
    val text = env.fromCollection(data).setParallelism(3)

    val filePath = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\output\\"
    text.writeAsText(filePath, WriteMode.OVERWRITE)

    env.execute("DataSinkApp")
  }
}
