package com.timzaak.controller

import play.api.mvc.Request

trait OAuthController extends Controller {

  def index = Action{implicit req:Request[_] =>
    Ok("world")
  }

}
