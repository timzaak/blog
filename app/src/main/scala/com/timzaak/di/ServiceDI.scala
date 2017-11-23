package com.timzaak.di

import akka.actor.Props
import com.timzaak.service.{SimpleService, SimpleServiceActor}

trait ServiceDI extends ActionDI{ di =>
  protected val sampleServiceActorRef = system.actorOf(Props[SimpleServiceActor], "simple")
  SimpleService.startService(sampleServiceActorRef)

}
