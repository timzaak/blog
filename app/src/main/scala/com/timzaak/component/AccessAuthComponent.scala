package com.timzaak.component

import com.timzaak.action.AccessAction
import com.timzaak.entity.User
import very.util.security.{ Access, BasicAccessDef, PermissionCheckable }

trait AccessAuthComponent[T <: BasicAccessDef] { self: T with PermissionCheckable =>

  protected def accessAction: AccessAction

  def accessDef: BasicAccessDef = this

  def withUserAccess(userId: UserId, access: (T => Access.Pos)*) = {
    accessAction.withUserAccess(userId, this, Access.union(access.map(_(this).toAccess): _*))
  }

  def withGroupAccess(groupId: GroupId, access: (T => Access.Pos)*) = {
    accessAction.withGroupAccess(groupId, this, Access.union(access.map(_(this).toAccess): _*))
  }

  def withGroupsAccess(groupIds: Seq[GroupId], access: (T => Access.Pos)*) = {
    accessAction.withGroupsAccess(groupIds, this, Access.union(access.map(_(this).toAccess): _*))
  }

  def withAccess(user: User, access: (T => Access.Pos)*) =
    accessAction.withAccess(user, this, Access.union(access.map(_(this).toAccess): _*))

}
