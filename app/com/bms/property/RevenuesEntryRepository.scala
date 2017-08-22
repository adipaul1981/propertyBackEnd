package com.bms.property

import javax.inject.Inject

import anorm._
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}

object RevenuesEntryRepository {

  val FIELD_ID="id"
  val FIELD_PROPERTY_ID="propId"
  val FIELD_REVENUE_TYPE="revenueType"
  val FIELD_VALUE="valueRevenue"
  val FIELD_VACANCY_RATE="vacancyRate"

  val ALL_FIELDS:String =
    s"$FIELD_PROPERTY_ID,$FIELD_REVENUE_TYPE, $FIELD_VALUE, $FIELD_VACANCY_RATE"

  val TABLE_NAME:String = "bms.revenues"
}

trait RevenuesEntryRepository{
  def save(revenues: Seq[Revenues], id:Int): Future[Unit]

//  def getRevenueByPropertyExpenseID (id:Int): Option[Revenues]

}

