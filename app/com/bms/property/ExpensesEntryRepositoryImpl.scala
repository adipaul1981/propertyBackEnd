package com.bms.property

import javax.inject.Inject

import anorm.SqlParser.{double, int, str}
import anorm.{NamedParameter, RowParser, SQL}
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}

/**
  * Created by Adi on 2017-03-12.
  */
class ExpensesEntryRepositoryImpl @Inject() (
                                              @NamedDatabase("default") db: Database
                                            )(implicit ec: ExecutionContext) extends ExpensesEntryRepository {

  override def save(expenses: Expenses): Future[Unit] = Future(blocking {
    db.withConnection { implicit c =>
      val sql =
        s"""INSERT INTO ${ExpensesEntryRepository.TABLE_NAME} (${ExpensesEntryRepository.ALL_FIELDS}) VALUES ({property_exp_id},{mun_tax}
           ,{school_tax},{snow_removal},{administration},{insurance},{heating},{electricity},{maintenance},{others})""".stripMargin
      println("TOTO")
      SQL(sql)
        .on(ExpensesEntryRepositoryImpl.generateOn(expenses): _*)
//        .on('property_exp_id -> expenses.property_expenses_id, 'mun_tax -> expenses.municipalTax, 'school_tax -> expenses.schoolTax,
//        'snow_removal -> expenses.snowRemoval, 'administration -> expenses.administration,
//        'insurance -> expenses.insurance, 'heating -> expenses.heating,
//        'electricity -> expenses.electricity, 'maintenance -> expenses.maintenance,
//        'others -> expenses.others)
        .executeInsert()
    }
  })

  override def getExpenseByPropertyExpenseID(id: Int): Option[Expenses] =  {
    db.withConnection { implicit c =>
      val sql =
        s"""|SELECT * FROM ${ExpensesEntryRepository.TABLE_NAME}
            |           WHERE ${ExpensesEntryRepository.FIELD_PROPERTY_EXPENSES_ID} = {${ExpensesEntryRepository.FIELD_PROPERTY_EXPENSES_ID}}
           """.stripMargin
      SQL(sql)
        .on(s"${ExpensesEntryRepository.FIELD_PROPERTY_EXPENSES_ID}" -> id)
        .as(ExpensesEntryRepositoryImpl.expensesParser.*)
        .headOption
    }
  }
}


object ExpensesEntryRepositoryImpl {

  def generateOn(f: Expenses): Seq[NamedParameter] =
    Seq(
      NamedParameter(ExpensesEntryRepository.FIELD_PROPERTY_EXPENSES_ID, f.property_expenses_id),
      NamedParameter(ExpensesEntryRepository.FIELD_MUNICIPAL_TAX, f.municipalTax),
      NamedParameter(ExpensesEntryRepository.FIELD_SCHOOL_TAX, f.schoolTax),
      NamedParameter(ExpensesEntryRepository.FIELD_SNOW_REMOVAL, f.snowRemoval),
      NamedParameter(ExpensesEntryRepository.FIELD_ADMINISTRATION, f.administration),
      NamedParameter(ExpensesEntryRepository.FIELD_INSURANCE, f.insurance),
      NamedParameter(ExpensesEntryRepository.FIELD_HEATING, f.heating),
      NamedParameter(ExpensesEntryRepository.FIELD_ELECTRICITY, f.electricity),
      NamedParameter(ExpensesEntryRepository.FIELD_MAINTENANCE, f.maintenance),
      NamedParameter(ExpensesEntryRepository.FIELD_OTHERS, f.others)
    )

  def expensesParser: RowParser[Expenses] = {
    for {
      id <- int(ExpensesEntryRepository.FIELD_ID)
      property_expenses_id <- int(ExpensesEntryRepository.FIELD_PROPERTY_EXPENSES_ID)
      municipalTax <- double(ExpensesEntryRepository.FIELD_MUNICIPAL_TAX)
      schoolTax <- double(ExpensesEntryRepository.FIELD_SCHOOL_TAX)
      snowRemoval <- double(ExpensesEntryRepository.FIELD_SNOW_REMOVAL).?
      administration <- double(ExpensesEntryRepository.FIELD_ADMINISTRATION).?
      insurance <- double(ExpensesEntryRepository.FIELD_INSURANCE).?
      heating <- double(ExpensesEntryRepository.FIELD_HEATING).?
      electricity <- double(ExpensesEntryRepository.FIELD_ELECTRICITY).?
      maintenance <- double(ExpensesEntryRepository.FIELD_MAINTENANCE).?
      others <- double(ExpensesEntryRepository.FIELD_OTHERS).?
    } yield {
      Expenses(
        id = id,
        property_expenses_id = property_expenses_id,
        municipalTax = municipalTax,
        schoolTax = schoolTax,
        snowRemoval = snowRemoval,
        administration = administration,
        insurance = insurance,
        heating = heating,
        electricity = electricity,
        maintenance = maintenance,
        others = others
      )
    }
  }

}

