package com.timzaak.di

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import com.timzaak.controller.WebSocketController

import scala.concurrent.ExecutionContext
import ws.very.util.akka.websocket.ConnectedActor

trait ControllerDI extends ActionDI with ServiceDI { di =>

  object webSocketController extends WebSocketController {
    override implicit protected def system: ActorSystem = di.system

    override implicit protected def materializer: ActorMaterializer = di.materializer

    override implicit protected def executionContext: ExecutionContext = di.executionContext

    // this is for local actor
    override protected def genConnectionActor(sessionId:S): ActorRef = if(clusterEnabled){
      startClusterProxy(Some("session"))
    }else{
      //TODO: sampleServiceActorRef 应该是本地 Cluster Client dispatch
      ConnectedActor.props(sessionId, Props(classOf[ConnectedActor], sampleServiceActorRef))
    }
  }
}
