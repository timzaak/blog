package com.timzaak.action

import com.timzaak.dao.CommentDao
import com.timzaak.entity.Comment

trait CommentAction extends Action {
  protected def commentDao: CommentDao

  def myComments(userId: L, pageSize: I, page: I) = commentDao.pages(userId, pageSize, page)

  //TODO: change Comment to CommentArg or toId,fromId,content arguments
  def postComment(fromId:L,toId:L,content:S) = commentDao.createComment(fromId,toId,content)

}
