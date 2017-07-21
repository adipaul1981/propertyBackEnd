package com.bms.property


import controllers.ExpensesController
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Revenues (
                      id:Int=0,
                      property_expenses_id :Int,
                      residential: Option[Double],
                      commercial: Option[Double],
                      parking_garages: Option[Double],
                      others:Option[Double],
                      vacancy_rate_residential:Option[Double],
                      vacancy_rate_commercial:Option[Double],
                      vacancy_rate_parking:Option[Double],
                      vacancy_rate_others:Option[Double]
                    ){}


object Revenues {

  implicit val readsExpenses: Reads[Revenues] = (
    (JsPath \ "ID").read[Int] or Reads.pure(0) and
      (JsPath \ "Property_Expenses_ID").read[Int] and
      (JsPath \ "Residential").readNullable[Double] and
      (JsPath \ "Commercial").readNullable[Double] and
      (JsPath \ "Parking_Garages").readNullable[Double] and
      (JsPath \ "Others").readNullable[Double] and
      (JsPath \ "VacancyRateResidential").readNullable[Double] and
      (JsPath \ "VacancyRateCommercial").readNullable[Double] and
      (JsPath \ "VacancyRateParking").readNullable[Double] and
      (JsPath \ "VacancyRateOthers").readNullable[Double]
    )(Revenues.apply _)


  implicit val writesExpenses: Writes[Revenues] = (
    (JsPath \ "ID").write[Int] and
      (JsPath \ "Property_Expenses_ID").write[Int] and
      (JsPath \ "Residential").writeNullable[Double] and
      (JsPath \ "Commercial").writeNullable[Double] and
      (JsPath \ "Parking_Garages").writeNullable[Double] and
      (JsPath \ "Others").writeNullable[Double] and
      (JsPath \ "VacancyRateResidential").writeNullable[Double] and
      (JsPath \ "VacancyRateCommercial").writeNullable[Double] and
      (JsPath \ "VacancyRateParking").writeNullable[Double] and
      (JsPath \ "VacancyRateOthers").writeNullable[Double]
    )(unlift(Revenues.unapply))
}
