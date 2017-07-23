package com.bms.property

import javax.inject.Inject

import play.api.libs.functional.syntax._
import play.api.libs.json._
import com.bms.property.{Address, Expenses}
import com.sun.jndi.cosnaming.IiopUrl.Address

import scala.concurrent.{ExecutionContext, Future}
//import com.bms.property.ExpensesEntryRepositoryImpl

case class Property
(
  id:Int=0,
  mls_no: String,
  price: Double,
  muneval:Double,
  no1half:Int,no2half:Int,no3half:Int,no4half:Int,no5half:Int,no6half:Int,
  address: Address,
  expenses:Option[Seq[Expenses]],
  revenues:Option[Seq[Revenues]]
)
{
  override def toString = this.mls_no

//  def checkExpenses(id: Int):Future[Option[Expenses]] = ExpensesEntryRepositoryImpl.
}

object Property{ //extends LinkGenerator[Property]{


  //Order ir Reads and Writes is important!!!!


  implicit val readsAddress: Reads[Address] = (
  (JsPath \ "PropertyNumber").read[Double] and
  (JsPath \ "Street").read[String] and
  (JsPath \ "City").read[String] and
  (JsPath \ "Province").read[String] and
  (JsPath \ "Country").read[String]
  )(Address.apply _)

  implicit val writesAddress: Writes[Address] = (
  (JsPath \ "PropertyNumber").write[Double] and
  (JsPath \ "Street").write[String] and
  (JsPath \ "City").write[String] and
  (JsPath \ "Province").write[String] and
  (JsPath \ "Country").write[String]
  )(unlift(Address.unapply))

  implicit val readsExpenses: Reads[Expenses] = (
    (JsPath \ "ExpensesType").read[String] and
      (JsPath \ "Value").readNullable[Double]
    )(Expenses.apply _)


  implicit val writesExpenses: Writes[Expenses] = (
    (JsPath \ "ExpensesType").write[String] and
      (JsPath \ "Value").writeNullable[Double]
    )(unlift(Expenses.unapply))

    implicit val readsRevenues: Reads[Revenues] = (
      (JsPath \ "RevenuesType").read[String] and
        (JsPath \ "Value").readNullable[Double] and
        (JsPath \ "VacancyRate").readNullable[Double]
      )(Revenues.apply _)


    implicit val writesRevenues: Writes[Revenues] = (
      (JsPath \ "RevenuesType").write[String] and
        (JsPath \ "Value").writeNullable[Double] and
        (JsPath \ "VacancyRate").writeNullable[Double]
      )(unlift(Revenues.unapply))

  implicit val readsProperty: Reads[Property] = (
    (JsPath \ "ID").read[Int] or Reads.pure(0) and
      (JsPath \ "MLSNumber").read[String] and
      (JsPath \ "Price").read[Double] and
      (JsPath \ "MunicipalEvaluation").read[Double] and
      (JsPath \ "NoOneAndHalf").read[Int] and
      (JsPath \ "NoTwoAndHalf").read[Int] and
      (JsPath \ "NoThreeAndHalf").read[Int] and
      (JsPath \ "NoFourAndHalf").read[Int] and
      (JsPath \ "NoFiveAndHalf").read[Int] and
      (JsPath \ "NoSixAndHalf").read[Int] and
      (JsPath \ "Address").read[Address] and
      (JsPath \ "Expenses").readNullable[Seq[Expenses]] and
      (JsPath \ "Revenues").readNullable[Seq[Revenues]]
    )(Property.apply _)

  implicit val writesProperty: Writes[Property] = (
    (JsPath \ "ID").write[Int] and
      (JsPath \ "MLSNumber").write[String] and
      (JsPath \ "Price").write[Double] and
      (JsPath \ "SchoolTaxes").write[Double] and
      (JsPath \ "NoOneAndHalf").write[Int] and
      (JsPath \ "NoTwoAndHalf").write[Int] and
      (JsPath \ "NoThreeAndHalf").write[Int] and
      (JsPath \ "NoFourAndHalf").write[Int] and
      (JsPath \ "NoFiveAndHalf").write[Int] and
      (JsPath \ "NoSixAndHalf").write[Int] and
      (JsPath \ "Address").write[Address] and
      (JsPath \ "Expenses").writeNullable[Seq[Expenses]] and
      (JsPath \ "Revenues").writeNullable[Seq[Revenues]]
    )(unlift(Property.unapply))



  //  implicit val readsExpenses: Reads[Expenses] = (
  //    (JsPath \ "PropertyNumber").read[Double] and
  //      (JsPath \ "Street").read[String] and
  //      (JsPath \ "City").read[String] and
  //      (JsPath \ "Province").read[String] and
  //      (JsPath \ "Country").read[String]
  //    )(Expenses.apply _)
  //
  //  implicit val writesExpenses: Writes[Expenses] = (
  //    (JsPath \ "PropertyNumber").write[Double] and
  //      (JsPath \ "Street").write[String] and
  //      (JsPath \ "City").write[String] and
  //      (JsPath \ "Province").write[String] and
  //      (JsPath \ "Country").write[String]
  //    )(unlift(Expenses.unapply))

  //  implicit val readsRevenues: Reads[Revenues] = (
  //  (JsPath \ "RevenuesType").read[String] and
  //  (JsPath \ "Value").read[Double]
  //  )(Revenues.apply _)
  //
  //  implicit val writesRevenues: Writes[Revenues] = (
  //    (JsPath \ "RevenuesType").write[String] and
  //      (JsPath \ "Value").write[Double]
  //    )(unlift(Revenues.unapply))





//  override def generateLink(p: Property) = LinkGenerator.PropertyLinkGenerator.generateLink(p)

//  def getPropertyExpenses(p:Property):Expenses={new Expenses(1,1)}
//  def setPropertyExpenses(p:Property):Expenses={new Expenses(1,1)}
}
