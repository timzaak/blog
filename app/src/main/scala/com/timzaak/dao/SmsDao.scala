package com.timzaak.dao

import com.joyrec.util.db.redis.WithRedis

trait SmsDao extends WithRedis {
  protected def expireTime: I

  def saveCaptcha(mobile: S, code: S) = {
    redis.set(mobile, code)
    redis.expire(mobile, expireTime)
  }

  def getCaptcha(mobile: S) = {
    redis.get(mobile)
  }
}
