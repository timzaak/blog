package com.timzaak.di

import com.typesafe.config.ConfigFactory

trait ConfigDI extends WithConf {
  var conf = ConfigFactory.load()
}
