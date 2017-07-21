package com.bms.property

import controllers.ExpensesController
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Expenses (
                id:Int=0,
                property_expenses_id :Int,
                municipalTax: Double,
                schoolTax: Double,
                snowRemoval: Option[Double],
                administration:Option[Double],
                insurance: Option[Double],
                heating:Option[Double],
                electricity:Option[Double],
                maintenance:Option[Double],
                others:Option[Double]
               ){}


object Expenses {

  implicit val readsExpenses: Reads[Expenses] = (
      (JsPath \ "ID").read[Int] or Reads.pure(0) and
      (JsPath \ "Property_Expenses_ID").read[Int] and
      (JsPath \ "Municipal_Tax").read[Double] and
      (JsPath \ "School_Tax").read[Double] and
      (JsPath \ "SnowRemoval").readNullable[Double] and
      (JsPath \ "Administration").readNullable[Double] and
      (JsPath \ "Insurance").readNullable[Double] and
      (JsPath \ "Heating").readNullable[Double] and
      (JsPath \ "Electricity").readNullable[Double] and
      (JsPath \ "Maintenance").readNullable[Double] and
      (JsPath \ "Others").readNullable[Double]
    )(Expenses.apply _)


  implicit val writesExpenses: Writes[Expenses] = (
      (JsPath \ "ID").write[Int] and
      (JsPath \ "Property_Expenses_ID").write[Int] and
      (JsPath \ "School_Tax").write[Double] and
      (JsPath \ "Municipal_Tax").write[Double] and
      (JsPath \ "SnowRemoval").writeNullable[Double] and
      (JsPath \ "Administration").writeNullable[Double] and
      (JsPath \ "Insurance").writeNullable[Double] and
      (JsPath \ "Heating").writeNullable[Double] and
      (JsPath \ "Electricity").writeNullable[Double] and
      (JsPath \ "Maintenance").writeNullable[Double] and
      (JsPath \ "Others").writeNullable[Double]
    )(unlift(Expenses.unapply))
}
