package com.timzaak.entity

import io.github.algd.oauth.data.model.{User => U}


case class User(id: Long, email: Option[String], mobile: Option[String], password: String) extends U {
  assert(email.orElse(mobile).isDefined, "User is Error")
}
