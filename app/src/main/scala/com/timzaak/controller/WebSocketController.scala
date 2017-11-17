package com.timzaak.controller

import java.util.UUID

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import ws.very.util.akka.websocket.{ComingMsg, ConnectedMsg}

import scala.concurrent.{ExecutionContext, Future}

abstract class WebSocketController {
  implicit protected def system: ActorSystem

  implicit protected def materializer: ActorMaterializer

  implicit protected def executionContext: ExecutionContext

  protected def connectedActorRef: ActorRef

  //-Dwebsocket.frame.maxLength=1024k
  def webSocketFlow: Flow[Message, TextMessage.Strict, NotUsed] = {
    //TODO: identify ConnectedActor
    val uuid = UUID.randomUUID().toString
    val actorRef = connectedActorRef
    val in = Flow[Message].collect {
      case TextMessage.Strict(text) => Future.successful(ComingMsg(uuid, text))
      case TextMessage.Streamed(textStream) => textStream.runFold("")(_ + _).map(ComingMsg)
    }.mapAsync(1)(identity).to(Sink.actorRef(connectedActorRef, PoisonPill))
    val out = {
      Source.actorRef(32, OverflowStrategy.fail).mapMaterializedValue { outActorRef =>
        connectedActorRef ! ConnectedMsg(uuid, outActorRef)
      }.map((txt: String) => TextMessage(txt))
    }
    Flow.fromSinkAndSource(in, out)
  }
}

