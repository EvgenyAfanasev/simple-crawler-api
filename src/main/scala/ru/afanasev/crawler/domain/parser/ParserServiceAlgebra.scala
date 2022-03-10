package ru.afanasev.crawler.domain.parser

import ru.afanasev.crawler.domain.parser.model.Response

trait ParserServiceAlgebra[F[_]] {
  def parseUrls(urls: Seq[String]): F[Response]
}
