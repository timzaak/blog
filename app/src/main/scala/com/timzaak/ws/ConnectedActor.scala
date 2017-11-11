package com.timzaak.ws

import akka.actor.Actor

class ConnectedActor extends Actor {
  override def receive: Receive = {
    case txt: String =>

    ???
  }
}
