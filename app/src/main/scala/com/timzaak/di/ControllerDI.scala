package com.timzaak.di

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.timzaak.controller.WebSocketController

import scala.concurrent.ExecutionContext

trait ControllerDI extends ActionDI{ di =>

  object webSocketController extends WebSocketController {
    override implicit protected def system: ActorSystem = di.system

    override implicit protected def materializer: ActorMaterializer = di.materializer

    override implicit protected def executionContext: ExecutionContext = di.executionContext
  }
}
