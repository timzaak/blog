package com.timzaak.di

import akka.actor.{ActorRef, ActorSystem}
import akka.stream.ActorMaterializer
import com.timzaak.controller.WebSocketController

import scala.concurrent.ExecutionContext
import ws.very.util.akka.websocket.ConnectionSharding

trait ControllerDI extends ActionDI with ConnectionSharding { di =>

  lazy val WSActorRef = startClusterProxy()

  object webSocketController extends WebSocketController {
    override implicit protected def system: ActorSystem = di.system

    override implicit protected def materializer: ActorMaterializer = di.materializer

    override implicit protected def executionContext: ExecutionContext = di.executionContext

    override protected def genConnectionActor(sessionId:S): ActorRef = WSActorRef

    // this is for local actor
    //override protected def genConnectionActor(sessionId:S): ActorRef = ConnectedActor.props(sessionId, Props[ConnectedActor])
  }
}
