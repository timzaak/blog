package com.timzaak.coin.huobi


import java.io.ByteArrayInputStream
import java.util.UUID
import java.util.zip.GZIPInputStream

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.coin.huobi.api.websocket.HuobiWebSocketListener
import com.timzaak.coin.huobi.api.websocket.RequestTopic.RequestTopic
import okhttp3._
import okio.ByteString
import org.json4s.JValue
import org.json4s.JsonAST._
import ws.very.util.json.JsonHelperWithDoubleMode
import scala.collection.mutable.ListMap
import scala.concurrent.{Future, Promise}

class HuobiWebSocketClient(listener: HuobiWebSocketListener) extends ClassSlf4j with JsonHelperWithDoubleMode{self =>
  private val client = new OkHttpClient

  protected def wsUrl = "wss://api.huobi.pro/ws"

  private val commandMap: ListMap[String, Promise[Either[JValue, JValue]]] = ListMap.empty

  private def genRandomKey = UUID.randomUUID().toString

  private def decompress(compressed: ByteString)  = {
    val inputStream = new GZIPInputStream(new ByteArrayInputStream(compressed.toByteArray()))
    scala.io.Source.fromInputStream(inputStream).mkString
  }


  protected val _listener = new WebSocketListener {
    override def onOpen(webSocket: WebSocket, response: Response): U = {
      info(s"huobi ws client open")
      listener.onOpen(self)
    }

    override def onClosed(webSocket: WebSocket, code: I, reason: Str): U = {
      info(s"huobi ws client closed,$code, $reason")
      listener.onClosed(self,code, reason)
    }

    override def onFailure(webSocket: WebSocket, t: Throwable, response: Response): U = {
      error(s"client error", t)
      listener.onFailure(self,t)
    }

    override def onMessage(webSocket: WebSocket, bytes: ByteString): U = {
      val text = decompress(bytes)
      val data = parseJson(text)
      //debug(s"receive: $text")
      data \ "ping" match {
        case JNothing =>
          data \ "id" match {
            case JString(id) =>
              commandMap.get(id).foreach {promise =>
                  promise.success(Right(data))
                  commandMap -= id
              }
            case _ =>
              listener.onMessage(self, data)
          }
        case JInt(time) =>
          webSocket.send(toJson("pong"-> time))
      }
    }
  }

  private val ws = {
    val request = new Request.Builder().url(wsUrl).build()
    client.newWebSocket(request, _listener)
  }

  def subscribe(rt: RequestTopic): Future[Either[JValue, JValue]] = {
    val promise = Promise[Either[JValue, JValue]]
    val id = genRandomKey
    commandMap += id-> promise
    val command = toJson(("id",id)~("sub",rt))
    ws.send(command)
    info(s"send:$command")
    promise.future
  }

  def close():Unit = {
    ws.close(1000, null)
    commandMap.foreach{case (_, promise)=>
      promise.failure(new InterruptedException("connection closed"))
    }
    commandMap.clear()
  }

}
