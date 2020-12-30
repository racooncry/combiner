package flinktrain.course4

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

import scala.collection.mutable.ListBuffer

/**
  * @Author yangxw
  * @Date 2020-12-30 下午2:36
  * @Description 算子
  * @Version 1.0
  */
object DataSetTransformationApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    // map
    // mapFunction(env)

    // filter
    // filterFunction(env)

    // connection
    // mapPartitionFunction(env)

    // firstFunction
    // firstFunction(env)

    // flatMap
    flatMapFunction(env)
  }


  def flatMapFunction(env: ExecutionEnvironment): Unit = {
    val info = ListBuffer[String]()
    info.append("hadoop,spark")
    info.append("hadoop,flink")

    info.append("flink,flink")

    val data = env.fromCollection(info)
    data.print()

    data.map(_.split(",")).print()

    data.flatMap(_.split(",")).print()

    data.flatMap(_.split(",")).map((_, 1)).groupBy(0).sum(1).print()
  }


  def firstFunction(env: ExecutionEnvironment): Unit = {
    val info = ListBuffer[(Int, String)]()
    info.append((1, "Hadoop"))
    info.append((1, "Hadoop"))
    info.append((1, "Hadoop"))
    info.append((2, "Hadoop"))
    info.append((2, "Hbase"))
    info.append((3, "spark"))
    info.append((3, "Spring"))
    info.append((4, "Spring Boot"))
    val data = env.fromCollection(info)
    data.first(3).print();
    println("~~~~~~~~分割线~~~~~~~~~")
    data.groupBy(0).first(2).print()
    println("~~~~~~~~分割线~~~~~~~~~")
    data.groupBy(0).sortGroup(1, Order.ASCENDING).first(2).print()
  }

  def mapFunction(env: ExecutionEnvironment): Unit = {
    val data = env.fromCollection(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    data.print()

    // 作用在每一个元素只上
    data.map((x: Int) => x + 1).print()
    data.map((x) => x + 1).print()
    data.map(x => x + 1).print()
    data.map(_ + 1).print()
  }

  def filterFunction(env: ExecutionEnvironment): Unit = {
    val data = env.fromCollection(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    data.map(_ + 1).filter(_ > 5).print()
  }


  // DataSource 100个元素，把结果存储到数据库中
  def mapPartitionFunction(env: ExecutionEnvironment): Unit = {
    val students = new ListBuffer[String]
    for (i <- 1 to 100) {
      students.append("student: " + i)
    }
    val data = env.fromCollection(students).setParallelism(4)
    //    data.map(x => {
    //      // 每一个元素要存储到数据库中去，肯定要先获取一个connection
    //      val connection = DBUtils.getConnection()
    //      println(connection + "....")
    //
    //      // TODO... 保存数据到DB
    //      DBUtils.returnConnection(connection)
    //    }).print()

    data.mapPartition(x => {
      val connection = DBUtils.getConnection()
      println(connection + "....")

      // TODO... 保存数据到DB
      DBUtils.returnConnection(connection)
      x
    }).print()

  }
}
