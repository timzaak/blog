package com.timzaak.di

import com.typesafe.config.ConfigFactory
import ws.very.util.akka.util.Confable
import ws.very.util.config.ConfigurationImplicit

trait ConfigDI extends Confable with ConfigurationImplicit{
  val conf = ConfigFactory.load("private.conf")
}
