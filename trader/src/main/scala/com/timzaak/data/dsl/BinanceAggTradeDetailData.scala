package com.timzaak.data.dsl

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.api.binance.BinanceWSClient
import com.timzaak.api.binance.entity.AggTrade
import monix.execution.Cancelable
import monix.execution.cancelables.BooleanCancelable
import monix.reactive.Observable
import monix.reactive.observers.Subscriber
import okhttp3.WebSocket
import org.json4s.JsonAST.JValue
import ws.very.util.json.JsonHelperWithDoubleMode

class BinanceAggTradeDetailData(symbol:S) extends Observable[AggTrade] with ClassSlf4j with JsonHelperWithDoubleMode{self =>
  private var client:Option[WebSocket] = None
  private var subscribers: List[Subscriber[AggTrade]] = Nil
  //TODO. cancable has problem
  private val cancelable = BooleanCancelable {()=>
    info("binance cancel")
    subscribers.foreach(_.onComplete())
    subscribers = Nil
    client.foreach(_.close(1000,null))
    client = None
  }

  private val _listener =  new BinanceWSClient.TradeListener{

    override def onMessage(message:JValue):Unit = {
      val aggTrade = message.extract[AggTrade]
      subscribers.foreach(_.onNext(aggTrade))
    }

    override def onClosed():Unit ={
      if(!cancelable.isCanceled){
        self.client = Option(generateClient)
      }
    }

    override def onFailure( t: Throwable):Unit = {
      if(!cancelable.isCanceled){
        self.client = Option(generateClient)
      }

    }
  }


  private def generateClient: WebSocket = (new BinanceWSClient).aggTradeStream(symbol, _listener)

  override def unsafeSubscribeFn(subscriber: Subscriber[AggTrade]): Cancelable = {
    subscribers = subscriber::subscribers
    client = client orElse Some(generateClient)
    cancelable
  }
}
