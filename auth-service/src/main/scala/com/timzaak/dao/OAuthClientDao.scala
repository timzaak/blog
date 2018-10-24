package com.timzaak.dao

import com.timzaak.oauth.entity.DefaultOAuthClient
import scalikejdbc.WrappedResultSet
import skinny.orm.Alias
import skinny.orm.feature.TimestampsFeatureWithId

trait OAuthClientDao extends TimestampsFeatureWithId[Int, DefaultOAuthClient] {
  override def idToRawValue(id: I): Any = id

  override def rawValueToId(rawValue: Any): I = rawValue.toString.toInt

  override val tableName = "auth_client"

  override def defaultAlias: Alias[DefaultOAuthClient] = createAlias("a_c")

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[DefaultOAuthClient]): DefaultOAuthClient = {
    DefaultOAuthClient(
      id = rs.get(n.id),
      client_id = rs.get(n.client_id),
      client_secret = rs.get(n.client_secret),
      name = rs.get(n.name),
      status = rs.get(n.status),
      redirect_url = rs.get(n.redirect_url),
      created_at = rs.get(n.created_at),
      updated_at = rs.get(n.updated_at)
    )
  }
}
