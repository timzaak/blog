package com.timzaak.data.dsl

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import com.timzaak.api.huobi.entity.Trade
import monix.execution.Cancelable
import monix.reactive.Observable
import monix.reactive.observers.Subscriber

import scala.concurrent.ExecutionContext

class HuobiTradeDetailDataV2(symbol:String) (implicit ec:ExecutionContext)  extends ClassSlf4j{

//  override def unsafeSubscribeFn(subscriber: Subscriber[Trade]): Cancelable = ???
}
