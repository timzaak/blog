package com.timzaak.entity

import com.timzaak.oauth.entity.OAuthUser
case class User(id:Int, name:String, password:String, email: String) extends OAuthUser{

}
