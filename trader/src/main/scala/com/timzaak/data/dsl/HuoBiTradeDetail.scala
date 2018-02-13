package com.timzaak.data.dsl

import com.timzaak.coin.huobi.HuobiWebSocketClient
import com.timzaak.coin.huobi.api.common.entity.Trade
import com.timzaak.coin.huobi.api.websocket.response.ChResponse
import com.timzaak.coin.huobi.api.websocket.{HuobiWebSocketListener, RequestTopic}
import monix.execution.Cancelable
import monix.reactive.observers.Subscriber
import monix.reactive.Observable
import org.json4s.JValue

//TODO: 重试策略
//TODO: 看看 Cats.State 编码范式
class HuoBiTradeDetail(symbol: S) extends Observable[Trade] {
  protected var wsClient: Option[HuobiWebSocketClient] = None
  protected var subscribers:List[Subscriber[Trade]] = Nil

  protected var cancelable = Cancelable { () =>
    wsClient.foreach(_.close())
    wsClient = None
    subscribers.foreach(_.onComplete)
    subscribers = Nil
  }
  private val _listener = new HuobiWebSocketListener {
    override def onOpen(client: HuobiWebSocketClient): U = {
      client.subscribe(RequestTopic.tradeDetail(symbol))
    }

    override def onMessage(client: HuobiWebSocketClient, message: JValue): U = {
      val tick = ChResponse.extract(message) match {
        case ChResponse(_, _, tick: Trade) => tick
      }
      subscribers.foreach(_.onNext(tick))
    }
    override def onFailure(client:HuobiWebSocketClient, t:Throwable):Unit = {
      wsClient = Some(createWebSocketClient)
    }

    override def onClosed(client: HuobiWebSocketClient, code: I, reason: S): U = {
      wsClient = Some(createWebSocketClient)
    }
  }

  private def createWebSocketClient = new HuobiWebSocketClient(_listener)

  override def unsafeSubscribeFn(subscriber: Subscriber[Trade]): Cancelable = {
    subscribers = subscriber :: subscribers
    wsClient = wsClient.orElse(Some(createWebSocketClient))
    cancelable
  }

}
