package com.timzaak.entity

import java.time.LocalDateTime

case class RoleGroup(
                      id: O[GroupId],
                      parentId: O[GroupId],
                      groupName: S,
                      description: O[S],
                      updatedAt: LocalDateTime = LocalDateTime.now()
                    )
