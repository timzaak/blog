import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory


object Server extends App  {
  val conf = ConfigFactory.load()
  println("======================",conf.getInt("akka.remote.netty.tcp.port"))
  val system = ActorSystem("app", conf)
  system.actorOf(Props[SimpleClusterListener], name = "clusterListener")
}


