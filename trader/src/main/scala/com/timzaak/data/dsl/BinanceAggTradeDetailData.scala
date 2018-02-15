package com.timzaak.data.dsl

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.api.binance.BinanceWSClient
import monix.execution.Cancelable
import monix.execution.cancelables.BooleanCancelable
import monix.reactive.Observable
import monix.reactive.observers.Subscriber
import okhttp3.WebSocket
import org.json4s.JsonAST.JValue

class BinanceAggTradeDetailData(symbol:S) extends Observable[String] with ClassSlf4j{self =>
  private var client:Option[WebSocket] = None
  private var subscribers: List[Subscriber[String]] = Nil
  private val cancelable = BooleanCancelable {()=>
    subscribers.foreach(_.onComplete())
    subscribers = Nil
    client.foreach(_.close(1000,null))
    client = None
  }

  private val _listener =  new BinanceWSClient.TradeListener{

    override def onMessage(client:BinanceWSClient, message:JValue):Unit = {
    }

    override def onClosed(client:BinanceWSClient):Unit ={

    }

    override def onFailure(client:BinanceWSClient, t: Throwable):Unit = {
      self.client = Option(generateClient)
    }
  }


  private def generateClient: WebSocket = (new BinanceWSClient).aggTradeStream(symbol, _listener)

  override def unsafeSubscribeFn(subscriber: Subscriber[String]): Cancelable = {
    subscribers = subscriber::subscribers
    client = client orElse(Some(generateClient))
    cancelable
  }
}
