package com.timzaak.currency

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import monix.execution.atomic.Atomic
//deprecated
class SimpleCurrencyProvider(asset:D) extends CurrencyProvider with ClassSlf4j{
  private val _asset = Atomic(asset)
  override def withdrawCurrency:D = {
    val result = _asset.getAndSet(0)
    info(s"get assets $result")
    result
  }


  override def addCurrency(asset:D): D =  {
    info(s"add assets $asset")
    _asset.addAndGet(asset)
  }

  override def getCurrency:D = {
    _asset.get
  }
}
