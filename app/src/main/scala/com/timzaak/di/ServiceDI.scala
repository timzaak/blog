package com.timzaak.di

import akka.actor.{ActorRef, Props}
import com.timzaak.service.{SimpleService, SimpleServiceActor}

trait ServiceDI extends ActionDI with ShardingDI { di =>
  protected val sampleServiceActorRef:ActorRef = system.actorOf(Props[SimpleServiceActor], "simple")



  SimpleService.startClusterService(sampleServiceActorRef)



}
