package com.timzaak.di

import com.typesafe.config.ConfigFactory
import very.util.config.WithConf

trait ConfigDI extends WithConf {
  var conf = ConfigFactory.load()
}
