package com.timzaak.api.binance.api.ws.listener

class WebSocketListener[Client, Message] {

  def onOpen(client:Client):Unit = {}

  def onMessage(client:Client, message:Message):Unit = {}

  def onClosed(client:Client):Unit ={}

  def onFailure(client:Client, t: Throwable):Unit = {}
}
