package com.bms.property

import javax.inject.Inject

import anorm._
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}

object RevenuesEntryRepository {

  val FIELD_ID="expenses_id"
  val FIELD_PROPERTY_EXPENSES_ID="property_exp_id"
  val FIELD_RESIDENTIAL="residential"
  val FIELD_COMMERCIAL="commercial"
  val FIELD_PARKING_GARAGE="parking_garage"
  val FIELD_OTHERS="others"
  val FIELD_VR_RESIDENTIAL="vacancy_rate_residential"
  val FIELD_VR_COMMERCIAL="vacancy_rate_commercial"
  val FIELD_VR_PARKING="vacancy_rate_parking"
  val FIELD_VR_OTHERS="vacancy_rate_others"

  val ALL_FIELDS:String =
    s"$FIELD_PROPERTY_EXPENSES_ID,$FIELD_RESIDENTIAL, $FIELD_COMMERCIAL, $FIELD_PARKING_GARAGE, $FIELD_OTHERS, $FIELD_VR_RESIDENTIAL," +
      s"$FIELD_VR_COMMERCIAL, $FIELD_VR_PARKING, $FIELD_VR_OTHERS"

  val TABLE_NAME:String = "bms.revenues"
}

trait RevenuesEntryRepository{
  def save(revenues: Revenues): Future[Unit]

  def getRevenueByPropertyExpenseID (id:Int): Option[Revenues]

}

