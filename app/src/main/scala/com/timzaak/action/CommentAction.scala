package com.timzaak.action

import com.timzaak.dao.CommentDao

trait CommentAction extends Action {
  def commentDao: CommentDao

  def myComments(userId: S, pageSize: I, page: I) = commentDao.pages(userId, pageSize, page)

}
