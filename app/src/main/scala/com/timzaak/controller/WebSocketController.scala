package com.timzaak.controller

import akka.NotUsed
import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import com.timzaak.ws._

import scala.concurrent.{ExecutionContext, Future}

abstract class WebSocketController {
  implicit protected def system: ActorSystem

  implicit protected def materializer: ActorMaterializer

  implicit protected def executionContext: ExecutionContext

  //-Dwebsocket.frame.maxLength=1024k
  def webSocketFlow:Flow[Message, TextMessage.Strict, NotUsed] = {
    //TODO: identify ConnectedActor
    val connectedActorRef = system.actorOf(Props[ConnectedActor])
    val in = Flow[Message].collect {
      case TextMessage.Strict(text) => Future.successful(ComingMsg(text))
      case TextMessage.Streamed(textStream) => textStream.runFold("")(_ + _).map(ComingMsg)
    }.mapAsync(1)(identity).to(Sink.actorRef(connectedActorRef, PoisonPill))
    val out = {
      Source.actorRef(32, OverflowStrategy.fail).mapMaterializedValue { outActorRef =>
        connectedActorRef ! ConnectedMsg(outActorRef)
      }.map((outMsg: OutMsg) => TextMessage(outMsg.txt))
    }
    Flow.fromSinkAndSource(in, out)
  }
}

