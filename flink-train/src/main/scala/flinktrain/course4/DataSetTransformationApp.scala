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
    // flatMapFunction(env)

    // distinct
    // distinctFunction(env)

    // joined
    // joinedFunction(env)

    // outer joined
    // outerJoinedFunction(env)

    // 笛卡尔积
    crossFunction(env)
  }


  def crossFunction(env: ExecutionEnvironment): Unit = {
    val info1 = List("曼联", "曼城")
    val info2 = List(3, 1, 0)

    val data1 = env.fromCollection(info1)
    val data2 = env.fromCollection(info2)

    data1.cross(data2).print()
  }


  def outerJoinedFunction(env: ExecutionEnvironment): Unit = {
    // 编号 名字
    val info = ListBuffer[(Int, String)]()
    info.append((1, "PK哥"))
    info.append((2, "J哥"))
    info.append((3, "H哥"))
    info.append((4, "G哥"))

    // 编号 城市
    val info2 = ListBuffer[(Int, String)]()
    info2.append((1, "北京"))
    info2.append((2, "上海"))
    info2.append((3, "天津"))
    info2.append((5, "深圳"))

    val data1 = env.fromCollection(info)
    val data2 = env.fromCollection(info2)

    data1.leftOuterJoin(data2).where(0).equalTo(0).apply((first, second) => {
      if (second == null) {
        (first._1, first._2, "-")
      } else {
        (first._1, first._2, second._2)
      }

    }).print()

    println("~~~~~~分隔符~~~~~~~")
    data1.rightOuterJoin(data2).where(0).equalTo(0).apply((first, second) => {
      if (first == null) {
        (second._1, "-", second._2)
      } else {
        (first._1, first._2, second._2)
      }

    }).print()

    println("~~~~~~分隔符~~~~~~~")
    data1.fullOuterJoin(data2).where(0).equalTo(0).apply((first, second) => {
      if (first == null) {
        (second._1, "-", second._2)
      } else if (second == null) {
        (first._1, first._2, "-")
      } else {
        (first._1, first._2, second._2)
      }

    }).print()

  }

  def joinedFunction(env: ExecutionEnvironment): Unit = {
    // 编号 名字
    val info = ListBuffer[(Int, String)]()
    info.append((1, "PK哥"))
    info.append((2, "J哥"))
    info.append((3, "H哥"))
    info.append((4, "G哥"))

    // 编号 城市
    val info2 = ListBuffer[(Int, String)]()
    info2.append((1, "北京"))
    info2.append((2, "上海"))
    info2.append((3, "天津"))
    info2.append((4, "深圳"))

    val data1 = env.fromCollection(info)
    val data2 = env.fromCollection(info2)

    data1.join(data2).where(0).equalTo(0).apply((first, second) => {
      (first._1, first._2, second._2)
    }).print()


  }

  def distinctFunction(env: ExecutionEnvironment): Unit = {
    val info = ListBuffer[String]()
    info.append("hadoop,spark")
    info.append("hadoop,flink")

    info.append("flink,flink")

    val data = env.fromCollection(info)
    data.flatMap(_.split(",")).distinct().print()

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
