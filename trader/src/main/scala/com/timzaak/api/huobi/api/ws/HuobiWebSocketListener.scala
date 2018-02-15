package com.timzaak.api.huobi.api.ws

import com.timzaak.api.huobi.HuobiWSClient
import org.json4s.JValue

trait HuobiWebSocketListener {
  def onOpen(client:HuobiWSClient):Unit = {}

  def onMessage(client:HuobiWSClient, message:JValue):Unit = {}

  def onClosed(client:HuobiWSClient, code: I, reason: S):Unit ={}

  def onFailure(client:HuobiWSClient, t: Throwable):Unit = {}
}
