package com.timzaak.dao

import com.timzaak.oauth.entity.DefaultOAuthAuthorization
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet
import skinny.orm.Alias
import skinny.orm.feature.CRUDFeatureWithId

class OAuthAuthorizationDao extends CRUDFeatureWithId[Int, DefaultOAuthAuthorization]{
  override def idToRawValue(id: I): Any = id

  override def rawValueToId(rawValue: Any): I = rawValue.toString.toInt

  override val tableName = "auth_auth"

  override def defaultAlias: Alias[DefaultOAuthAuthorization] = createAlias("aa")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[DefaultOAuthAuthorization]): DefaultOAuthAuthorization = {
    DefaultOAuthAuthorization(
      rs.get[String](n.authorization),
      rs.get[String](n.code),
      rs.get[String](n.client_id),
      rs.get[Int](n.user_id),
      rs.get[DateTime](n.created_at),
      rs.get[DateTime](n.deleted_at))
  }
}
