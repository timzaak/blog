package com.timzaak.ws

import akka.actor.{Actor, ActorRef, PoisonPill}

sealed trait ConnMessage

case class ComingMsg(txt: String) extends ConnMessage

case class OutMsg(txt: String) extends ConnMessage

case class ConnectedMsg(actorRef: ActorRef) extends ConnMessage

object ClosingMsg extends ConnMessage

class ConnectedActor extends Actor {
  var client :ActorRef = _
  override def receive: Receive = {
    case ComingMsg(txt) =>
      println(s"coming.$txt")
      client ! OutMsg(txt)

    case ConnectedMsg(actorRef) =>
      println(s"connected")
      client = actorRef
      client ! OutMsg("connected")
  }

  override def postStop(): Unit = {
    client ! PoisonPill
    super.postStop()
  }
}
