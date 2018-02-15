package com.timzaak.api.binance.api.ws.listener

class WebSocketListener[Message] {

  def onOpen():Unit = {}

  def onMessage(message:Message):Unit = {}

  def onClosed():Unit ={}

  def onFailure(t: Throwable):Unit = {}
}
