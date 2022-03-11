package ru.afanasev.crawler.domain.parser

import ru.afanasev.crawler.domain.parser.model.Response

trait ParserServiceAlgebra[F[_]] {

  /**
  * Parse urls and return site's title
  * @param urls accepted sequence of urls
  * @return new Response which contains success result
  *   or/and fail result of parsing
  */
  def parseUrls(urls: Seq[String]): F[Response]
}
