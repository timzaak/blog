package com.timzaak.di

import com.typesafe.config.ConfigFactory
import ws.very.util.akka.util.Confable

/**
  * Created by timzaak on 17/6/9.
  */
trait WithTestConf extends Confable {
  val conf = ConfigFactory.load()
}
