package com.bms.property

import javax.inject.Inject

import anorm._
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}


/**
  * Created by Adi on 2016-10-21.
  */
object ExpensesEntryRepository {

  val FIELD_ID="expenses_id"
  val FIELD_PROPERTY_EXPENSES_ID="property_exp_id"
  val FIELD_MUNICIPAL_TAX="mun_tax"
  val FIELD_SCHOOL_TAX="school_tax"
  val FIELD_SNOW_REMOVAL="snow_removal"
  val FIELD_ADMINISTRATION="administration"
  val FIELD_INSURANCE="insurance"
  val FIELD_HEATING="heating"
  val FIELD_ELECTRICITY="electricity"
  val FIELD_MAINTENANCE="maintenance"
  val FIELD_OTHERS="others"

  val ALL_FIELDS:String =
    s"$FIELD_PROPERTY_EXPENSES_ID,$FIELD_MUNICIPAL_TAX, $FIELD_SCHOOL_TAX, $FIELD_SNOW_REMOVAL, $FIELD_ADMINISTRATION, $FIELD_INSURANCE," +
      s"$FIELD_HEATING, $FIELD_ELECTRICITY, $FIELD_MAINTENANCE, $FIELD_OTHERS"

  val TABLE_NAME:String = "bms.expenses"
}

trait ExpensesEntryRepository{
  def save(expenses: Expenses): Future[Unit]

  def getExpenseByPropertyExpenseID (id:Int): Option[Expenses]

}


