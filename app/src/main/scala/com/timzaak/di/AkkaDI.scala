package com.timzaak.di

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait AkkaDI {
  implicit val system = ActorSystem("app")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
}
