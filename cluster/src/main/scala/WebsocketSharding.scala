import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import ws.very.util.akka.websocket.{ConnectedActor, ConnectionSharding}

object WebsocketSharding extends App with ConnectionSharding{
  val conf = ConfigFactory.load("session.conf")
  val system = ActorSystem("app", conf)
  startClusterSharding(Props[ConnectedActor])
}
