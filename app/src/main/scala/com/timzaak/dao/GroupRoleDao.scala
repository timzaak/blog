package com.timzaak.dao

import com.timzaak.entity.RoleGroup
import very.util.db.postgre.PostgresPlainProfileWithJson4S.api._

import scala.concurrent.Future

trait GroupRoleDao extends Dao {
  override def tableName = "groups"

  override protected val fieldList: List[String] =
    extractSnakeFields[RoleGroup]

  def setRoleGroup(roleGroup: RoleGroup): Future[GroupId] = {
    import roleGroup._
    id match {
      case Some(i) =>
        sql"update #$tableName(parent_id,group_name,description) values ($parentId,$groupName,$description) where id=$i returning id"
          .as[GroupId]
          .head
      case _ =>
        sql"insert into #$tableName(parent_id,group_name,description) values ($parentId,$groupName,$description) returning id"
          .as[GroupId]
          .head
    }
  }

}
