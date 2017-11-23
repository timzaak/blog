import akka.actor.{ActorSystem, Props}
import akka.cluster.client.{ClusterClient, ClusterClientSettings}
import com.typesafe.config.ConfigFactory
import ws.very.util.akka.websocket.{ConnectedActor, ConnectionSharding}
// sbt "runMain Session -Dconfig.resource=session.conf"
object Session extends App with ConnectionSharding{
  val conf = ConfigFactory.load("session.conf")
  implicit val system = ActorSystem("app", conf)

  val serviceProxy = system.actorOf(ClusterClient.props(
    ClusterClientSettings(system)), "session_service_proxy")
  startClusterSharding(Props(classOf[ConnectedActor],serviceProxy))
}
