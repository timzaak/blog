package com.timzaak.component

import com.timzaak.action.AccessAction
import com.timzaak.entity.User
import very.util.security.{ Access, BasicAccessDef, PermissionCheckable }
import scala.language.implicitConversions

trait AccessAuthComponent[T <: BasicAccessDef] { self: T with PermissionCheckable =>

  protected def accessAction: AccessAction

  def accessDef: BasicAccessDef = this

  private implicit def transfer(access: Seq[(T => Access.Pos)]): Access =
    Access.union(access.map(_(this).toAccess): _*)

  def withUserAccess(userId: UserId, access: (T => Access.Pos)*) = {
    accessAction.withUserAccess(userId, this, access)
  }

  def withGroupAccess(groupId: GroupId, access: (T => Access.Pos)*) = {
    accessAction.withGroupAccess(groupId, this, access)
  }

  def withGroupsAccess(groupIds: Seq[GroupId], access: (T => Access.Pos)*) = {
    accessAction.withGroupsAccess(groupIds, this, access)
  }

  def withAccess(user: User, access: (T => Access.Pos)*) =
    accessAction.withAccess(user, this, access)

}
