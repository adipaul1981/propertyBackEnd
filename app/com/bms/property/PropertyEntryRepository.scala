package com.bms.property

import javax.inject.Inject

import anorm._
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}


/**
  * Created by Adi on 2016-10-21.
  */
object PropertyEntryRepository {

  val FIELD_ID="property_id"
  val FIELD_MLS="mls_no"
  val FIELD_PRICE="price"
  val FIELD_MUNICIPAL_EVAL="municipal_evaluation"
  val FIELD_NO1_AND_HALF="no_1_and_half"
  val FIELD_NO2_AND_HALF="no_2_and_half"
  val FIELD_NO3_AND_HALF="no_3_and_half"
  val FIELD_NO4_AND_HALF="no_4_and_half"
  val FIELD_NO5_AND_HALF="no_5_and_half"
  val FIELD_NO6_AND_HALF="no_6_and_half"
  val FIELD_STREET_NO="street_no"
  val FIELD_STREET="street"
  val FIELD_CITY="city"
  val FIELD_PROVINCE="province"
  val FIELD_COUNTRY="country"

  val FIELD_PROP_ID="propId"

  val ALL_FIELDS:String =
    s"$FIELD_MLS,$FIELD_PRICE, $FIELD_MUNICIPAL_EVAL, $FIELD_NO1_AND_HALF, $FIELD_NO2_AND_HALF, $FIELD_NO3_AND_HALF," +
      s"$FIELD_NO4_AND_HALF, $FIELD_NO5_AND_HALF, $FIELD_NO6_AND_HALF, $FIELD_STREET_NO, $FIELD_STREET, $FIELD_CITY, $FIELD_PROVINCE, $FIELD_COUNTRY"

  val TABLE_NAME:String = "bms.potentialproperty"
  val TABLE_NAME_EXPENSES:String = "bms.expenses"
  val TABLE_NAME_REVENUES:String = "bms.revenues"
}

trait PropertyEntryRepository{
  def saveProperty(property: Property): Future[Unit]
//  def saveExpenses(expenses: Seq[Expenses],id:Int): Future[Unit]
//  def saveRevenues(revenues: Seq[Revenues],id:Int): Future[Unit]
//  def delete(id: Int) :Future[Unit]
  def get(mls: String): Option[Property]
//  def getList: List[Property]

}


