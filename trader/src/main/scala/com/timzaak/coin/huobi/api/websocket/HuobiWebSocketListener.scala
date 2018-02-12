package com.timzaak.coin.huobi.api.websocket

import com.timzaak.coin.huobi.HuobiWebSocketClient
import org.json4s.JValue

trait HuobiWebSocketListener {
  def onOpen(client:HuobiWebSocketClient):Unit

  def onMessage(client:HuobiWebSocketClient,message:JValue):Unit

  def onClosed(client:HuobiWebSocketClient,code: I, reason: S):Unit

  def onFailure(client:HuobiWebSocketClient,t: Throwable):Unit
}
