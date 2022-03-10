package ru.afanasev.crawler.domain.parser

import cats._
import cats.data._
import cats.effect._
import cats.implicits._

import com.typesafe.scalalogging.Logger

import org.jsoup.Jsoup

import ru.afanasev.crawler.domain.parser.model.Result._
import ru.afanasev.crawler.domain.parser.model.Url._
import ru.afanasev.crawler.domain.parser.model._

import scala.util.Try

class ParserServiceInterpreter[F[_] : Parallel : Sync](log: Logger) extends ParserServiceAlgebra[F] {

  override def parseUrls(urls: Seq[String]): F[Response] =
    urls.parTraverse(url => parseUrl(Url.mkUrl(url)))
      .map(result => result.foldLeft(Response.empty) { (acc, value) =>
        value match {
          case Success(value) => acc.copy(success = acc.success + value)
          case Fail(value) => acc.copy(fail = acc.fail + value)
        }
      })

  def parseUrl(url: Either[UrlAndResult, Valid]): F[Result] = {
    val result = (for {
      url <- EitherT.fromEither[F](url)
      title <- EitherT(getTitle(url.value))
    } yield title).value

    result.map(_.onError(err => log.debug(s"${err._1} can not processing [${err._2}]").asRight) match {
      case Left(err) => Fail(err)
      case Right(value) => Success(value)
    })
  }

  def getTitle(url: String): F[Either[UrlAndResult, UrlAndResult]] = Sync[F].delay {
    Try {
      url ->
        Jsoup.connect(url)
          .get()
          .title()
    }.toEither.leftMap(url -> _.getMessage)
  }
}

object ParserServiceInterpreter {
  def apply[F[_] : Parallel : Sync]: Kleisli[F, Logger, ParserServiceInterpreter[F]] =
    Kleisli(log => new ParserServiceInterpreter[F](log).pure[F])
}
