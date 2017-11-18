package com.timzaak.di

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ws.very.util.akka.util.{AkkaSystemable, Confable}

trait AkkaDI extends AkkaSystemable with Confable {
  implicit val system           = ActorSystem("app", conf)
  implicit val materializer     = ActorMaterializer()
  implicit val executionContext = system.dispatcher
}
