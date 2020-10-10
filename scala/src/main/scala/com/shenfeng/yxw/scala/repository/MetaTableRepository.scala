package com.shenfeng.yxw.scala.repository


import com.shenfeng.yxw.scala.domain.MetaTable
import org.springframework.data.repository.CrudRepository

trait MetaTableRepository extends CrudRepository[MetaTable, Integer] {

}
