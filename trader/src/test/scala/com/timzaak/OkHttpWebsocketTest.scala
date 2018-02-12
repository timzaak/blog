package com.timzaak

import com.timzaak.di.ConfigDI
import okhttp3._
import org.scalatest._
import ws.very.util.json.JsonHelperWithDoubleMode

// sbt "testOnly com.timzaak.OkHttpWebsocketTest"
class OkHttpWebsocketTest extends FreeSpec with Matchers with ConfigDI with JsonHelperWithDoubleMode{
  "hello world" in {
    val client = new OkHttpClient
    val request = new Request.Builder().url("wss://echo.websocket.org").build()
    val websocketListener = new WebSocketListener{
      override def onOpen(webSocket: WebSocket, response: Response): Unit = {
        println("on Open ok....")
      }

      override def onMessage(webSocket: WebSocket, text: String): Unit = {
        println("on Text", text)
      }

      override def onFailure(webSocket: WebSocket, t: Throwable, response: Response): Unit = {
        println("on Failure")
      }

      override def onClosed(webSocket: WebSocket, code: Int, reason: String): Unit = {
        println("on close", code, reason)
      }
    }


    val webSocket = client.newWebSocket(request, websocketListener)
    webSocket.send("hello")
    Thread.sleep(1000)
    webSocket.close(1000, null)
    Thread.sleep(20*1000)
  }
}
