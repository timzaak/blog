import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory


object Server extends App  {
  val conf = ConfigFactory.load()
  val system = ActorSystem("app", conf)
  system.actorOf(Props[SimpleClusterListener], name = "clusterListener")
}


