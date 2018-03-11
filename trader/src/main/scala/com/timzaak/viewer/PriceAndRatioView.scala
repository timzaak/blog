package com.timzaak.viewer


abstract class PriceAndRatioView() {

  def beginPrice:D
  def nowPrice:D
  def expectedRatio:Option[D]
  def expectedPrice:Option[D]

}



