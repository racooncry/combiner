package flinktrain.course4

import com.shenfeng.yxw.flinktrain.course4.Person
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration

/**
  * @Author yangxw
  * @Date 2020-12-30 下午1:59
  * @Description https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/batch/
  * @Version 1.0
  */
object DataSetDataSourceApp {

  def main(args: Array[String]): Unit = {

    // step1:
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 从集合读
    // fromCollection(env)

    // 从文件读
    // textFile(env)

    // 从csv读
    // csv(env)

    // 递归读
    // readRecursiveFiles(env)

    // 读压缩文件
    readCompressionFiles(env)
  }

  def readRecursiveFiles(env: ExecutionEnvironment): Unit = {
    val path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\inputRecursive"
    env.readTextFile(path).print()
    println("~~~~~~~分割线~~~~~~~")

    val parameters = new Configuration
    // set the recursive enumeration parameter
    parameters.setBoolean("recursive.file.enumeration", true)
    env.readTextFile(path).withParameters(parameters).print()

  }

  def readCompressionFiles(env: ExecutionEnvironment): Unit = {
    val path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\compressive"
    env.readTextFile(path).print()
  }

  def fromCollection(env: ExecutionEnvironment): Unit = {
    val data = 1 to 10
    env.fromCollection(data).print()
  }


  def textFile(env: ExecutionEnvironment): Unit = {
    // val path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\input\\file.text"

    val path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\inputs"
    env.readTextFile(path).print()
  }


  def csv(env: ExecutionEnvironment): Unit = {
    val path = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\flink-train\\src\\main\\resources\\input\\people.csv"
    // env.readCsvFile[(String, Int, String)](path, ignoreFirstLine = true).print()
    // env.readCsvFile[(String, Int)](path, ignoreFirstLine = true, includedFields = Array(0, 1)).print()

    case class MyCaseClass(name: String, age: Int)
    // env.readCsvFile[MyCaseClass](path,ignoreFirstLine = true, includedFields = Array(0, 1)).print()

    env.readCsvFile[Person](path, ignoreFirstLine = true, pojoFields = Array("name", "age", "word")).print()

  }


}
