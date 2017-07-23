package com.bms.property

import javax.inject.Inject

import anorm._
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}

object ExpensesEntryRepository {

  val FIELD_ID="id"
  val FIELD_PROPERTY_ID="propId"
  val FIELD_EXPENSE_TYPE="expenseType"
  val FIELD_VALUE="value"

  val ALL_FIELDS:String =
    s"$FIELD_PROPERTY_ID,$FIELD_EXPENSE_TYPE, $FIELD_VALUE"

  val TABLE_NAME:String = "bms.expenses"
}

//trait ExpensesEntryRepository{
//  def save(expenses: Expenses): Future[Unit]
//
//  def getExpenseByPropertyExpenseID (id:Int): Option[Expenses]
//
//}


