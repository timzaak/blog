package com.timzaak.dao

import com.joyrec.util.db.redis.WithRedis

trait SmsDao{
  protected def expireTime: I

  def saveCaptcha(mobile: S, code: S) = {
//    redis.set(mobile, code)
//    redis.expire(mobile, expireTime)
    1L
  }

  def getCaptcha(mobile: S) = {
//    redis.get(mobile)
    Option("1")
  }
}
