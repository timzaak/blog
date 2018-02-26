package com.timzaak.assets

import monix.eval.Task

trait AssetsProvider {
  def withdrawAssets:D
  def addAssets(asset:D):D
  def getAssets:D
}
