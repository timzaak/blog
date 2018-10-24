package com.timzaak.dao

import com.timzaak.entity.User
import scalikejdbc.WrappedResultSet
import skinny.orm.feature.CRUDFeatureWithId
import skinny.orm.Alias

trait UserDao extends Dao with CRUDFeatureWithId[Int, User]{

  override def idToRawValue(id: Int): Any = {
    id
  }

  override def rawValueToId(rawValue: Any): Int = {
    rawValue.toString.toInt
  }

  override val tableName = "users"

  override def defaultAlias: Alias[User] = createAlias("us")

  private def c = column

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User = {
    User(
      id = rs.get[Int](n.id),
      name = rs.get[String](n.name),
      password = rs.get[String](n.password),
      email = rs.get[String](n.email)
    )
  }

}
