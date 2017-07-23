package com.bms.property


import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Revenues (
                      revenueType:String,
                      value:Option[Double],
                      vacancyRate:Option[Double]
                    ){}


object Revenues {

  implicit val readsExpenses: Reads[Revenues] = (
      (JsPath \ "RevenueType").read[String] and
      (JsPath \ "Value").readNullable[Double] and
      (JsPath \ "VacancyRate").readNullable[Double]
    )(Revenues.apply _)


  implicit val writesExpenses: Writes[Revenues] = (
      (JsPath \ "RevenueType").write[String] and
      (JsPath \ "Value").writeNullable[Double] and
      (JsPath \ "VacancyRate").writeNullable[Double]
    )(unlift(Revenues.unapply))
}
