package flinktrain.course4

import scala.util.Random

/**
  * @Author yangxw
  * @Date 2020-12-30 下午2:47
  * @Description
  * @Version 1.0
  */
object DBUtils {

  def getConnection(): String = {
    new Random().nextInt(10) + ""
  }

  def returnConnection(connection: String): Unit = {

  }

}
