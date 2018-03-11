package com.timzaak.currency

import monix.eval.Task

trait CurrencyProvider {
  def withdrawCurrency:D
  def addCurrency(asset:D):D
  def getCurrency:D
}
