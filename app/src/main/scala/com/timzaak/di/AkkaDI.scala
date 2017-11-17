package com.timzaak.di

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import ws.very.util.akka.util.AkkaSystemable

trait AkkaDI extends AkkaSystemable{
  implicit val system           = ActorSystem("app")
  implicit val materializer     = ActorMaterializer()
  implicit val executionContext = system.dispatcher
}
