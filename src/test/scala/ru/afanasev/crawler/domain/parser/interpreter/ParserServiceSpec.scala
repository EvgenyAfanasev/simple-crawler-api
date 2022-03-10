package ru.afanasev.crawler.domain.parser.interpreter

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import ru.afanasev.crawler.domain.parser.ParserServiceInterpreter

class ParserServiceSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers with LazyLogging {

  val parserService: IO[ParserServiceInterpreter[IO]] = ParserServiceInterpreter[IO].run(logger)

  "Parsing urls" - {
    "When all urls correct" - {
      val urls = Seq(
        "https://yandex.ru/",
        "https://2gis.ru/moscow",
        "https://www.google.com/doodles/prof-udupi-ramachandra-raos-89th-birthday"
      )
      "response will not contains error" in {
        val action = for {
          service <- parserService
          result  <- service.parseUrls(urls)
        } yield result
        action.asserting(_.success.size shouldBe 3)
        action.asserting(_.fail.size shouldBe 0)
      }
    }

    "When urls aren't correct" - {
      val urls = Seq(
        "https://yandex.ru/",
        "ht/2gis.ru/moscow",
        "https://www.google.com/doodles/prof-udupi-ramachandra-raos-89th-birthday"
      )
      "response will contains 1 fail result" in {
        val action = for {
          service <- parserService
          result  <- service.parseUrls(urls)
        } yield result
        action.asserting(_.fail.size shouldBe 1)
        action.asserting(_.success.size shouldBe 2)
      }
    }

    "When response is bad" - {
      val urls = Seq(
        "https://yandex.ru/",
        "https://2gis.ru/moscow",
        "https://www.dasds12d31as.fqw/"
      )
      "response will contains 1 fail result" in {
        val action = for {
          service <- parserService
          result  <- service.parseUrls(urls)
        } yield result
        action.asserting(_.fail.size shouldBe 1)
        action.asserting(_.success.size shouldBe 2)
      }
    }

    "When all url are incorrect or response is bad" - {
      val urls = Seq(
        "ht/2gis.ru/moscow",
        "https://www.dasds12d31as.fqw/",
        "http:/www.linkedin.com/"
      )
      "response will contains only fail result" in {
        val action = for {
          service <- parserService
          result  <- service.parseUrls(urls)
        } yield result
        action.asserting(_.fail.size shouldBe 3)
        action.asserting(_.success.size shouldBe 0)
      }

    }
  }
}


