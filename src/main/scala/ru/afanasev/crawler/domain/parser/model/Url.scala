package ru.afanasev.crawler.domain.parser.model

import cats.implicits._

sealed abstract case class Url(value: String)

object Url {
  private val usernamePattern = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)".r

  private def apply(value: String) = new Url(value) {}

  def mkUrl(url: String): Either[(String, String), Url] =
    if(usernamePattern.matches(url)) Url(url).asRight
    else (url -> "url is incorrect").asLeft
}
