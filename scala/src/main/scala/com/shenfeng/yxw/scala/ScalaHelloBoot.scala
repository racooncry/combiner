package com.shenfeng.yxw.scala

import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RestController}

/**
  * @Author yangxw
  * @Date 2020-10-10 上午11:34
  * @Description
  * @Version 1.0
  */
@RestController
class ScalaHelloBoot {


  @RequestMapping(value = Array("say-scala-hello"), method = Array(RequestMethod.GET))
  def sayHello() = {
    "Hello Scala Boot"
  }
}
