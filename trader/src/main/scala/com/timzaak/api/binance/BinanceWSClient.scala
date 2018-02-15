package com.timzaak.api.binance

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.api.binance.api.BinanceWSApi
import okhttp3._
import com.timzaak.api.binance.api.ws.listener.{WebSocketListener => Listener}
import org.json4s.JValue

class BinanceWSClient extends BinanceWSApi with ClassSlf4j{self =>
  private val client = new OkHttpClient
  import BinanceWSClient._


  def aggTradeStream(symbol:S, tradeListener: TradeListener) = {
    val request = new Request.Builder().url(aggregateTradeStreamsUrl(symbol)).build()
    client.newWebSocket(request, new WebSocketListener {
      override def onOpen(webSocket: WebSocket, response: Response): U = {
        info("binance open")
        tradeListener.onOpen(self)
      }

      override def onClosed(webSocket: WebSocket, code: I, reason: Str): U = {
        info("binance Closed")
        tradeListener.onClosed(self)
      }

      override def onFailure(webSocket: WebSocket, t: Throwable, response: Response): U = {
        info("binance failue", t)
        tradeListener.onFailure(self, t)
      }

      override def onMessage(webSocket: WebSocket, text: Str): U = {
        println("text",text)
      }
    })
  }
}
object BinanceWSClient {
  type TradeListener = Listener[BinanceWSClient,JValue]
}