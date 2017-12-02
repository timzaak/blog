package com.timzaak.di

import ws.very.util.akka.util.Confable
import ws.very.util.akka.websocket.ConnectionSharding

trait ShardingDI extends ConnectionSharding with Confable{ di =>
  def clusterEnabled:B = conf.getOptional[B]("server.cluster.enabled").exists(identity)

}
