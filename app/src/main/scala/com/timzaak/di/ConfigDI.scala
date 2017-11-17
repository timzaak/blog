package com.timzaak.di

import com.typesafe.config.ConfigFactory
import ws.very.util.akka.util.Confable

trait ConfigDI extends Confable {
  var conf = ConfigFactory.load()
}
