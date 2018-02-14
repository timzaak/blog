package com.timzaak.data.dsl

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.coin.huobi.HuobiWebSocketClient
import com.timzaak.coin.huobi.api.common.entity.Trade
import com.timzaak.coin.huobi.api.websocket.response.ChResponse
import com.timzaak.coin.huobi.api.websocket.{HuobiWebSocketListener, RequestTopic}
import monix.execution.Cancelable
import monix.reactive.observers.Subscriber
import monix.reactive.Observable
import org.json4s.JValue
import scala.concurrent.ExecutionContext

//TODO: 重试策略
class HuobiTradeDetailData(symbol: S)(implicit ec:ExecutionContext) extends Observable[Trade] with ClassSlf4j{
  protected var wsClient: Option[HuobiWebSocketClient] = None
  protected var subscribers:List[Subscriber[Trade]] = Nil
  private var terminate = false
  protected var cancelable = Cancelable { () =>
    wsClient.foreach(_.close())
    wsClient = None
    subscribers.foreach(_.onComplete)
    subscribers = Nil
    terminate = true
  }
  private val _listener = new HuobiWebSocketListener {
    override def onOpen(client: HuobiWebSocketClient): U = {
      client.subscribe(RequestTopic.tradeDetail(symbol)).failed.map{ t=>
        error(t.getMessage)
        subscribers.foreach(_.onError(t))
        cancelable.cancel()
      }
    }

    override def onMessage(client: HuobiWebSocketClient, message: JValue): U = {
      val tick = ChResponse.extract(message) match {
        case ChResponse(_, _, tick: Trade) => tick
      }
      subscribers.foreach(_.onNext(tick))
    }
    override def onFailure(client:HuobiWebSocketClient, t:Throwable):Unit = {
      if(!terminate)
        wsClient = Option(createWebSocketClient)
    }

    override def onClosed(client: HuobiWebSocketClient, code: I, reason: S): U = {
      if(!terminate)
        wsClient = Option(createWebSocketClient)
    }
  }

  private def createWebSocketClient:HuobiWebSocketClient = new HuobiWebSocketClient(_listener)

  override def unsafeSubscribeFn(subscriber: Subscriber[Trade]): Cancelable = {
    subscribers = subscriber :: subscribers
    wsClient = wsClient.orElse(Some(createWebSocketClient))
    cancelable
  }

}
