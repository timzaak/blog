import akka.actor.{ActorSystem, Props}
import com.timzaak.seed.SimpleClusterListener
import com.typesafe.config.ConfigFactory
// sbt "runMain Seed -Dconfig.resource=seed.conf"
object Seed extends App  {
  val conf = ConfigFactory.load("seed.conf")
  implicit val system = ActorSystem("app", conf)
  system.actorOf(Props[SimpleClusterListener], name = "clusterListener")
}


