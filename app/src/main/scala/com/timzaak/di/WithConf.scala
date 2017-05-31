package com.timzaak.di

import com.typesafe.config.Config

trait WithConf {
  def conf: Config
}
