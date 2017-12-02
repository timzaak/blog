package com.timzaak.service

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.cluster.client.ClusterClientReceptionist
import ws.very.util.akka.websocket.{OutMsg, ServiceMsg, SessionServiceMsg}

object SimpleService {
  def startClusterService(serviceActorRef:ActorRef)(implicit system:ActorSystem) ={
    ClusterClientReceptionist(system).registerService(serviceActorRef)
  }
  def startLocalService(serviceActorRef:ActorRef)(implicit system:ActorSystem) = {

  }
}


class SimpleServiceActor(
) extends Actor {
  override def receive = {
    case SessionServiceMsg(sessionId,sessionActorRef, cmd) =>
      println(s"SimpleService: sessionId${sessionId}, cmd ${cmd}")
      sessionActorRef ! OutMsg(sessionId, s"SimpleService response: ${cmd}")
  }
}