import akka.actor.{ActorSystem, Props}
import com.timzaak.seed.SimpleClusterListener
import com.typesafe.config.ConfigFactory

object Seed extends App  {
  val conf = ConfigFactory.load("seed.conf")
  val system = ActorSystem("app", conf)
  system.actorOf(Props[SimpleClusterListener], name = "clusterListener")
}


