package com.timzaak.di

import com.typesafe.config.ConfigFactory

/**
  * Created by timzaak on 17/6/9.
  */
trait WithTestConf extends WithConf {
  val conf = ConfigFactory.load()
}
