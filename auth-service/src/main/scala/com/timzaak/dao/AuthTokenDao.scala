package com.timzaak.dao

import com.timzaak.oauth.entity.DefaultOAuthToken
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet
import skinny.orm.Alias
import skinny.orm.feature.CRUDFeatureWithId

class AuthTokenDao extends CRUDFeatureWithId[Int, DefaultOAuthToken]{
  override def idToRawValue(id: I): Any = id

  override def rawValueToId(rawValue: Any): I =  rawValue.toString.toInt

  override val tableName = "auth_token"

  override def defaultAlias: Alias[DefaultOAuthToken] = createAlias("at")

  def c=  this.column


  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[DefaultOAuthToken]): DefaultOAuthToken = {

    DefaultOAuthToken(
      rs.get[String](n.id),
      rs.get[Int](n.user_id),
      rs.get[String](n.client_id),
      rs.get[String](n.access_token),
      rs.get[String](n.refresh_token),
      rs.get[String](n.token_type),
      rs.get[DateTime](n.created_at),
      rs.get[DateTime](n.deleted_at),
      rs.get[Option[String]](n.scope))
  }
}
