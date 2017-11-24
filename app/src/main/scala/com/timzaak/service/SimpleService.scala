package com.timzaak.service

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.cluster.client.ClusterClientReceptionist
import ws.very.util.akka.websocket.{OutMsg, ServiceMsg}

object SimpleService {
  def startService(serviceActorRef:ActorRef)(implicit system:ActorSystem) ={
    ClusterClientReceptionist(system).registerService(serviceActorRef)
  }
}


class SimpleServiceActor(
  sessionActorRef: ActorRef
) extends Actor {
  override def receive = {
    case ServiceMsg(sessionId, path, cmd) =>
      println(s"SimpleService: sessionId${sessionId}, path ${path}, cmd ${cmd}")
      sessionActorRef ! OutMsg(sessionId, s"SimpleService response: ${cmd}")
  }
}