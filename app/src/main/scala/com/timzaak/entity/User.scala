package com.timzaak.entity

case class User(
                 id: UserId,
                 groupIds: Seq[GroupId]
               )
