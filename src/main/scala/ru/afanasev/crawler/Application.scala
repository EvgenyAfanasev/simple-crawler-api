package ru.afanasev.crawler

import cats._
import cats.effect._

import com.typesafe.scalalogging.LazyLogging

import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router

import ru.afanasev.crawler.domain.parser._

object Application extends IOApp with LazyLogging {

  override def run(args: List[String]): IO[ExitCode] = buildServer[IO].run(logger)
    .map(router => BlazeServerBuilder[IO].bindHttp(8080, "localhost").withHttpApp(router))
    .flatMap(server => server.serve.compile.drain).as(ExitCode.Success)


  def buildServer[F[_]: Monad: Async: Parallel] = for {
    service <- ParserServiceInterpreter[F]
    route   <- ParserRoute[F](service)
  } yield Router("/" -> route.request).orNotFound
}
