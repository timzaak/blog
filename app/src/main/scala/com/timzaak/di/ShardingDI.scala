package com.timzaak.di

import akka.actor.ActorRef
import ws.very.util.akka.websocket.ConnectionSharding

trait ShardingDI extends ConnectionSharding{ di =>
  lazy val sessionActorRef:ActorRef = startClusterProxy(Some("session"))


}
