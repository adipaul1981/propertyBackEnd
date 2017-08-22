package com.bms.property


import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Revenues (
                      revenueType: Option[String],
                      value: Option[Double],
                      vacancyRate: Option[Double]
                    )

object Revenues{

  implicit val readsRevenues: Reads[Revenues] = (
    (JsPath \ "RevenuesType").readNullable[String] and
      (JsPath \ "Value").readNullable[Double] and
      (JsPath \ "VacancyRate").readNullable[Double]
    )(Revenues.apply _)

  implicit val writesRevenues: Writes[Revenues] = (
    (JsPath \ "RevenuesType").writeNullable[String] and
      (JsPath \ "Value").writeNullable[Double] and
      (JsPath \ "VacancyRate").writeNullable[Double]
    )(unlift(Revenues.unapply))

}
