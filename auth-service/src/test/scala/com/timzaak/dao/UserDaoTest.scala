package com.timzaak.dao

import com.timzaak.di.DaoDI
import com.timzaak.entity.User
import org.scalatest.{FreeSpec, Matchers}
import scalikejdbc._
// sbt "testOnly com.timzaak.dao.UserDaoTest"
class UserDaoTest extends FreeSpec with Matchers with DaoDI{
  "findAll" in {
    userDao.findAll()(userDao.session) shouldBe empty
  }
  "insert/get/delete" in {
    val userInput = User(0,"test")
    val user = userInput.copy(id = userDao.insert(userInput))
    userDao.findById(user.id) shouldBe Some(user)
    userDao.updateBy(sqls.eq(userDao.column.name, "test"))
      .withNamedValues(userDao.column.name->"hello world")
    userDao.findById(user.id).map(_.name) shouldBe Some("hello world")
    userDao.deleteById(user.id)
    userDao.findById(user.id) shouldBe None


  }
}
