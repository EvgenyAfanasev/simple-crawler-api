package ru.afanasev.crawler.domain.parser.model

sealed trait Result

object Result {

  type UrlAndResult = (String, String)

  final case class Success(value:  UrlAndResult) extends Result

  final case class Fail(value: UrlAndResult) extends Result
}
