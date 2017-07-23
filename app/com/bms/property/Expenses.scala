package com.bms.property

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Expenses (
                expenseType:String,
                value:Option[Double]
               ){}


object Expenses {

  implicit val readsExpenses: Reads[Expenses] = (
      (JsPath \ "ExpenseType").read[String] and
      (JsPath \ "Value").readNullable[Double]
    )(Expenses.apply _)


  implicit val writesExpenses: Writes[Expenses] = (
    (JsPath \ "ExpenseType").write[String] and
      (JsPath \ "Value").writeNullable[Double]
    )(unlift(Expenses.unapply))
}
