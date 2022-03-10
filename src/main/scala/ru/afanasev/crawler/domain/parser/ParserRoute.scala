package ru.afanasev.crawler.domain.parser


import cats.implicits._
import cats.data.Kleisli

import cats.effect.kernel.Async
import com.typesafe.scalalogging.Logger

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe.CirceEntityCodec._

import io.circe.generic.auto._

class ParserRoute[F[_]: Async](parserService: ParserServiceAlgebra[F]) {

  implicit val dsl = Http4sDsl.apply[F]
  import dsl._

  def request: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "parser" => for {
      json     <- req.as[Seq[String]]
      result   <- parserService.parseUrls(json)
      response <- Ok(result)
    } yield response
  }
}

object ParserRoute {
  def apply[F[_]: Async](parserService: ParserServiceAlgebra[F]): Kleisli[F, Logger, ParserRoute[F]] =
    Kleisli(_ => new ParserRoute[F](parserService).pure[F])
}
