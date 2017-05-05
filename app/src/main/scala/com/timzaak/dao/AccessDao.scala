package com.timzaak.dao

import slick.jdbc.{GetResult, PositionedParameters, SetParameter}
import very.util.db.postgrel.BaseSqlDSL
import very.util.db.postgrel.PostgresProfileWithJson4S.api._
import very.util.security.Permission

import scala.concurrent.Future

trait AccessDao extends BaseSqlDSL {

  private object genId {
    val userId = (_: UserId) + "u"

    val groupId = (_: GroupId) + "g"
  }

  private implicit val getPermissionResult = GetResult(r => Permission(r.<<))

  private implicit object SetPermission extends SetParameter[Permission] {
    def apply(v: Permission, pp: PositionedParameters) {
      pp.setLong(v.self)
    }
  }

  private implicit object SetSeqString extends SetParameter[Seq[String]] {
    def apply(v: Seq[String], pp: PositionedParameters) {
      v.map(item => pp.setString(item.self))
    }
  }

  protected def findPermission(resource: S, id: S): Future[O[Permission]] = {
    sql"select permission from #${tableName} where resource=${resource} and id=${id} limit 1".as[Permission].headOption
  }

  def setPermission(id: S, resource: S, permission: Permission): Future[I] = {
    sqlu"""insert into #${tableName}(id,resource,permission) values ($id, $resource, $permission)
          on conflict(id,resource) do update set permission = $permission"""
  }

  def getUserPermission(resource: S, id: UserId) = findPermission(resource, genId.userId(id))

  def getGroupPermission(resource: S, id: GroupId) = findPermission(resource, genId.groupId(id))

  def getGroupsPermission(resource: S, ids: Seq[GroupId]): Future[Seq[Permission]] = {
    sql"select permission from #${tableName} where resource=${resource} and id in (${ids.map(genId.groupId): Seq[String]})".as[Permission]
  }
}
