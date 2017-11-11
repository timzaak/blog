package com.timzaak.ws

import akka.actor.{Actor, ActorContext, ActorRef, ActorSystem, PoisonPill, Props}

sealed trait ConnMessage

case class ComingMsg(txt: String) extends ConnMessage

case class OutMsg(txt: String) extends ConnMessage

case class ConnectedMsg(actorRef: ActorRef) extends ConnMessage

object ClosingMsg extends ConnMessage


object ConnectedActor {
  def props(uid: String)(implicit system: ActorSystem) = {
    system.actorOf(Props(new ConnectedActor(uid)), s"$uid")
  }

  def _localActorUserPath(uid:String) = s"/$uid"

  def getActorRef(uid: String)(implicit system: ActorSystem) = {
    system.actorSelection(_localActorUserPath(uid))
  }
}

class ConnectedActor(uid: String) extends Actor {
  var client: ActorRef = _

  override def receive: Receive = {
    case ComingMsg(txt) =>

      println(s"coming.$txt")
      client ! OutMsg(txt)
    case msg: OutMsg =>
      client ! msg
    case ConnectedMsg(actorRef) =>
      println(s"connected")
      client = actorRef
      client ! OutMsg("connected")
  }

  override def postStop(): Unit = {
    //for test, we need to check if null
    if (client != null) client ! PoisonPill
    super.postStop()
  }
}