package com.timzaak.dao

import io.github.algd.oauth.data.model.Client
import very.util.db.postgre.PostgresProfileWithJson4S.api._

trait WithClientTable {
  class ClientTable(tag: Tag) extends Table[Client](tag, "client") {

    def id = column[String]("id", O.PrimaryKey)
    def name = column[String]("name")
    def scope = column[List[String]]("scope")
    def allowedGrants = column[List[String]]("allowed_grants")
    def redirectUris = column[List[String]]("redirect_uris")


    val tuppled:((String,String,List[String],List[String],List[String])) => Client = {case (name:String, id:String,scope:List[String], allowedGrants:List[String], redirectUris:List[String]) =>
      Client(name,id,scope.toSet, allowedGrants.toSet,redirectUris)
    }


    val unappled:Client => Option[(String,String,List[String],List[String],List[String])] = (client:Client) =>
      Some((client.name,client.id,client.scope.toList,client.allowedGrants.toList, client.redirectUris))

    def * = (name, id,scope, allowedGrants, redirectUris) <> (tuppled,unappled)
  }

}

trait ClientDao extends Dao with WithClientTable{

}
