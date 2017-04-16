package com.timzaak.di

import com.typesafe.config.ConfigFactory

object WithConf{
  val conf = ConfigFactory.load()
}
trait WithConf {
  def conf = WithConf.conf
}
