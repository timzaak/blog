package com.timzaak.controller

import java.util.UUID

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.{ActorMaterializer, OverflowStrategy}
import ws.very.util.akka.websocket.{ClosingMsg, ConnMessage, ConnectedMsg, EnableHeartBeat, PingMsg, ServiceMsg}

import scala.concurrent.{ExecutionContext, Future}

abstract class WebSocketController {
  implicit protected def system: ActorSystem

  implicit protected def materializer: ActorMaterializer

  implicit protected def executionContext: ExecutionContext

  protected def genConnectionActor(sessionId:S): ActorRef

  protected def handleMessage(uuid:String):PartialFunction[String,ConnMessage] ={
    case msg if msg.length > 2 =>
      println("....")
      ServiceMsg(uuid,"simple", msg)
    case msg =>
      println("ping Msg")
      PingMsg(uuid,msg)

  }
  //-Dwebsocket.frame.maxLength=1024k
  def webSocketFlow: Flow[Message, TextMessage.Strict, NotUsed] = {
    //TODO: identify ConnectedActor
    val uuid = UUID.randomUUID().toString
    val actorRef = genConnectionActor(uuid)
    val in = Flow[Message].collect {
      case TextMessage.Strict(text) =>
        Future.successful(handleMessage(uuid)(text))
      case TextMessage.Streamed(textStream) =>
        textStream.runFold("")(_ + _).map(handleMessage(uuid))
    }.mapAsync(1)(identity).to(Sink.actorRef(actorRef, ClosingMsg(uuid)))
    val out = {
      Source.actorRef(32, OverflowStrategy.fail).mapMaterializedValue { outActorRef =>

        actorRef ! ConnectedMsg(uuid, outActorRef)
        actorRef ! EnableHeartBeat(uuid, 3)
      }.map((txt: String) => TextMessage(txt))
    }
    Flow.fromSinkAndSource(in, out)
  }
}

