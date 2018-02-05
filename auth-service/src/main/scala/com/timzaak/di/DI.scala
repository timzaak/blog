package com.timzaak.di

import com.timzaak.controller.OAuthController
import play.api.mvc._



trait DI {

}

trait DIWithPlay extends play.api.BuiltInComponents {

  implicit def controllerComponents:ControllerComponents

  object OAuthController extends OAuthController




}