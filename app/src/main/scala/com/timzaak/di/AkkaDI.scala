package com.timzaak.di

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait AkkaDI {
  implicit def system = ActorSystem("app")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
}
