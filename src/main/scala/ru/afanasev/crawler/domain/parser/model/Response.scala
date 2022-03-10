package ru.afanasev.crawler.domain.parser.model

final case class Response(success: Map[String, String], fail: Map[String, String])

object Response {
  def empty: Response = Response(Map.empty, Map.empty)
}