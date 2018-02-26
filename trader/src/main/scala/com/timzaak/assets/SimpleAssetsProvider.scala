package com.timzaak.assets

import com.joyrec.util.log.impl.slf4j.ClassSlf4j
import monix.execution.atomic.Atomic

class SimpleAssetsProvider(asset:D) extends AssetsProvider with ClassSlf4j{
  private val _asset = Atomic(asset)
  override def withdrawAssets:D = {
    val result = _asset.getAndSet(0)
    info(s"get assets $result")
    result
  }


  override def addAssets(asset:D): D =  {
    info(s"add assets $asset")
    _asset.addAndGet(asset)
  }

  override def getAssets:D = {
    _asset.get
  }
}
