package com.timzaak.di

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.timzaak.controller.WebSocketController

import scala.concurrent.ExecutionContext
import ws.very.util.akka.websocket.{ConnectedActor, ConnectionSharding}

trait ControllerDI extends ActionDI with ConnectionSharding { di =>

  lazy val WSActorRef = startCusterSharding(Props[ConnectedActor])

  object webSocketController extends WebSocketController {
    override implicit protected def system: ActorSystem = di.system

    override implicit protected def materializer: ActorMaterializer = di.materializer

    override implicit protected def executionContext: ExecutionContext = di.executionContext

    override protected def connectedActorRef: ActorRef = WSActorRef
  }
}
