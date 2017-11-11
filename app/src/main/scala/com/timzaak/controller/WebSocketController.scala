package com.timzaak.controller

import akka.actor.{ActorSystem, Props}
import akka.stream.scaladsl.{Flow, Sink}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.ActorMaterializer
import com.timzaak.ws.ConnectedActor

import scala.concurrent.ExecutionContext
trait WebSocketController  {
  implicit def system :ActorSystem
  implicit def materializer :ActorMaterializer
  implicit def executionContext:ExecutionContext

  def webSocketFlow() = {
    val connectedActorRef = system.actorOf(Props[ConnectedActor], s"u_")
    Flow[Message].collect{
      case TextMessage.Strict(text) => text
        connectedActorRef ! text
        Sink.actorRefWithAck(connectedActorRef)
      case TextMessage.Streamed(s) => s

    }
  }
}

