package com.timzaak

import ws.very.util.lang.Implicits2

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

trait TypeImplicit extends Implicits2 {
  type UserId = L
  type MobileNum = S
  type Captcha = S

  import scala.language.implicitConversions

  implicit def TryFutureToFuture[T](f: Try[Future[T]]):Future[T] = f match {
    case Success(s) => s
    case Failure(e) => Future.failed(e)
  }

  implicit def ExceptionToFutureException[T,E<:Throwable](e:E):Future[T] = Future.failed(e)

}
