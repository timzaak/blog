package test

import com.timzaak.coin.huobi.HuobiWebSocketClient
import com.timzaak.coin.huobi.api.common.entity.Trade
import com.timzaak.coin.huobi.api.websocket.{HuobiWebSocketListener, RequestTopic}
import com.timzaak.coin.huobi.api.websocket.RequestTopic.RequestTopic
import com.timzaak.coin.huobi.api.websocket.response.ChResponse
import org.json4s.JValue
import ws.very.util.json.JsonHelperWithDoubleMode

import scala.concurrent.ExecutionContext.Implicits.global

//sbt "runMain test.FollowCheck"
object FollowCheck extends App with JsonHelperWithDoubleMode{
  // Huobi 本身的数据，相差约 30~300毫秒，初始的链接的第一个返回值 可能会相差到 10秒
  // 数据通过网络到本机+序列化，单个监听相差 15毫秒~200毫秒（假设本机时钟和 Huobi 一致），随着监听的越多，延迟越高
  // 最终实时数据延迟 100毫秒 ~ 500毫秒，中位数错略算应该在 200~240 毫秒之间，最大延迟 883毫秒
  // 也就是能做到秒级交易。。

  // WebSocket 链接会因为不可知原因断掉(框架 or 服务器端), 断掉感知7s。需要做客户端心跳重试机制
  val checkTime= (v:JValue) => {
    ChResponse.extract(v) match {
      case ChResponse(_, ts, tick:Trade) =>
        val mill = now.mill
        println(ts ,ts - tick.ts, now.mill - ts, mill -tick.ts)
    }
  }
  def subscribe(rt:RequestTopic, func:JValue => Unit) = new HuobiWebSocketListener {
    override def onOpen(client: HuobiWebSocketClient): U = {
      client.subscribe(rt).failed.foreach{e=>
        e.printStackTrace()
      }
    }

    override def onMessage(client: HuobiWebSocketClient, message: JValue): U = {
      func(message)
    }

    override def onClosed(client: HuobiWebSocketClient, code: I, reason: S): U = {
      println("close...",code,reason)
    }

    override def onFailure(client: HuobiWebSocketClient, t: Throwable): U = {
      println("throwin...", now.mill)
    }
  }


  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("bchusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("etcusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("ethusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("btcusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("ltcusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("eosusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("xrpusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("omgusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("dashusdt"),checkTime))
//  new HuobiWebSocketClient(subscribe(RequestTopic.tradeDetail("zecusdt"),checkTime))

}
