package com.timzaak.ws

import akka.actor.ActorSystem
import akka.testkit._
import org.scalatest._

class ConnectedActorTest extends TestKit(ActorSystem("test"))
  with FreeSpecLike with Matchers
  with ImplicitSender with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "conn" - {
    "can be got by path" in {
      val uid = "12312312"
      val ref = system.actorOf(TestActors.echoActorProps, uid)

      assert(ref.path.toString.endsWith(s"/user/$uid"), "path check error")
      system.actorSelection(s"/user/$uid") ! "123"
      expectMsg("123")
    }
  }

}
