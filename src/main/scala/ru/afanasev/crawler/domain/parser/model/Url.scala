package ru.afanasev.crawler.domain.parser.model

import cats.implicits._
import io.estatico.newtype.macros.newtype
import ru.afanasev.crawler.domain.parser.model.Result.UrlAndResult



object Url {

  @newtype sealed abstract case class Valid(value: String)

  private val urlPattern = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)".r

  def mkUrl(url: String): Either[UrlAndResult, Valid] =
    if(urlPattern.matches(url)) Valid(url).asRight
    else (url -> "url is incorrect").asLeft
}
