package com.timzaak.seed

import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import ws.very.util.akka.actor.Actor

class SimpleClusterListener extends Actor {
  val cluster = Cluster(context.system)

  override def preStart(): Unit = {
    cluster.subscribe(self, classOf[MemberEvent], classOf[UnreachableMember])
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
  }

  override def receive: Receive = {
    case state: CurrentClusterState =>
      println(s"Current members: ${state.members.mkString(", ")}")
    case MemberUp(member) =>
      println(s"Member is Up: ${member.address}")
    case UnreachableMember(member) =>
      println(s"Member detected as unreachable: $member")
    case MemberRemoved(member, previousStatus) =>
      println(s"Member is Removed: ${member.address} after $previousStatus")
    case _: MemberEvent => // ignore
  }
}
