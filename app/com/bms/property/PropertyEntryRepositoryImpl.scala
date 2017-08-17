package com.bms.property

import javax.inject.Inject

import anorm.SqlParser.double
import anorm._
import play.api.db.{ Database, NamedDatabase }
//import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}


class PropertyEntryRepositoryImpl @Inject() (
  @NamedDatabase("default") db: Database
)(implicit ec: ExecutionContext) extends PropertyEntryRepository {

  override def saveProperty(property: Property): Future[Unit]= Future(blocking {
    db.withConnection{implicit c => {
      val sqlProperty =
        s"""INSERT INTO ${PropertyEntryRepository.TABLE_NAME} (${PropertyEntryRepository.ALL_FIELDS}) VALUES ({mls},{price},{muneval}
           ,{onehalf},{twohalf},{threehalf},{fourhalf},{fivehalf},{sixhalf},{street_no},{street},{city},{province},{country})""".stripMargin

      SQL(sqlProperty)
      .on('mls -> property.mls_no, 'price -> property.price, 'muneval -> property.muneval,
        'onehalf -> property.no1half, 'twohalf -> property.no2half, 'threehalf -> property.no3half, 'fourhalf -> property.no4half,
        'fivehalf -> property.no5half, 'sixhalf -> property.no6half, 'street_no -> property.address.streetNum, 'street -> property.address.street, 'city -> property.address.city,
        'province -> property.address.province, 'country -> property.address.country)
      .executeInsert() match
        {
        case Some(id) => {
          if (property.expenses nonEmpty) {
            val sqlExpenses =
              s"""INSERT INTO ${ExpensesEntryRepository.TABLE_NAME} (${ExpensesEntryRepository.ALL_FIELDS})
            VALUES """ + PropertyEntryRepositoryImpl.parameterRowsExpenses(property, id.asInstanceOf[Int])._1
            SQL(sqlExpenses)
              .on(PropertyEntryRepositoryImpl.parameterRowsExpenses(property, id.asInstanceOf[Int])._2: _ *)
              .executeUpdate()
          }
          if (property.revenues nonEmpty) {
            val sqlRevenues =
              s"""INSERT INTO ${RevenuesEntryRepository.TABLE_NAME} (${RevenuesEntryRepository.ALL_FIELDS})
          VALUES """ + PropertyEntryRepositoryImpl.parameterRowsRevenues(property, id.asInstanceOf[Int])._1
            SQL(sqlRevenues)
              .on(PropertyEntryRepositoryImpl.parameterRowsRevenues(property, id.asInstanceOf[Int])._2: _ *)
              .executeUpdate()
          }
        }
        case None => Nil
      }


    }}
  })

//  override def delete(id: Int): Future[Unit]= Future(blocking {
//    db.withConnection{implicit c =>
//      val sql =
//        s"""DELETE FROM ${PropertyEntryRepository.TABLE_NAME}  WHERE (${PropertyEntryRepository.FIELD_ID}={property_id})""".stripMargin
//      SQL(sql)
//        .on('property_id -> id)
//        .executeInsert()
//    }
//  })

  //TODO Construct the "WHERE" String (make  get really generic)
  override def get(str: String): Option[Property] = {

    db.withConnection{implicit c =>
      val sql =
        s"""|SELECT * FROM ${PropertyEntryRepository.TABLE_NAME}
            |LEFT JOIN bms.expenses ON bms.expenses.propId = bms.potentialproperty.property_id
            |LEFT JOIN bms.revenues ON bms.revenues.propId = bms.potentialproperty.property_id
            |           WHERE ${PropertyEntryRepository.FIELD_MLS} = {mls}
           """.stripMargin
      println("Adrian")
      println(sql)
      println(PropertyEntryRepositoryImpl.propertyParser)
      val l = SQL(sql)
        .on('mls -> str)
        .as(PropertyEntryRepositoryImpl.propertyParser.*)
      l.headOption map{h =>
        h.copy(expenses = l.flatMap(_.expenses).distinct, revenues = l.flatMap(_.revenues).distinct)
      }

    }
  }

//  override def getList : List[Property] = {
//
//    db.withConnection{implicit c =>
//      val sql =
//
//        s"""|SELECT * FROM ${PropertyEntryRepository.TABLE_NAME} LIMIT 2000
//           """.stripMargin
//      SQL(sql)
//        .as(PropertyEntryRepositoryImpl.propertyParser.*)
//    }
//  }
}

object PropertyEntryRepositoryImpl{
  import anorm.SqlParser.{ str, int,double}

  def propertyParser:RowParser[Property] = {
    for {
      id <- int(PropertyEntryRepository.FIELD_ID)
      mls_no <- str(PropertyEntryRepository.FIELD_MLS)
      price <- double(PropertyEntryRepository.FIELD_PRICE)
      muneval <-  double(PropertyEntryRepository.FIELD_MUNICIPAL_EVAL)
      no1half <- int(PropertyEntryRepository.FIELD_NO1_AND_HALF)
      no2half <- int(PropertyEntryRepository.FIELD_NO2_AND_HALF)
      no3half <- int(PropertyEntryRepository.FIELD_NO3_AND_HALF)
      no4half <- int(PropertyEntryRepository.FIELD_NO4_AND_HALF)
      no5half <- int(PropertyEntryRepository.FIELD_NO5_AND_HALF)
      no6half <- int(PropertyEntryRepository.FIELD_NO6_AND_HALF)
      address <- addressParser
      expenses <- expensesParser
      revenues <- revenueParser
    } yield {Property(
      id = id,
      mls_no = mls_no,
      price = price,
      muneval = muneval,
      no1half = no1half,
      no2half = no2half,
      no3half = no3half,
      no4half = no4half,
      no5half = no5half,
      no6half = no6half,
      address = address,
      expenses = Seq(expenses),
      revenues = Seq(revenues)

    )}
  }

  def addressParser:RowParser[Address] = {
    for {
      streetNum <- double(PropertyEntryRepository.FIELD_STREET_NO)
      street <- str(PropertyEntryRepository.FIELD_STREET)
      city <- str(PropertyEntryRepository.FIELD_CITY)
      province <- str(PropertyEntryRepository.FIELD_PROVINCE)
      country <- str(PropertyEntryRepository.FIELD_COUNTRY)
    } yield {Address(
      streetNum = streetNum,
      street = street,
      city = city,
      province = province,
      country = country
    )}

  }

  def expensesParser:RowParser[Expenses] = {
    for {
      expenseType <- str(ExpensesEntryRepository.FIELD_EXPENSE_TYPE).?
      value <- double(ExpensesEntryRepository.FIELD_VALUE).?
    } yield {Expenses(
      expenseType = expenseType,
      value = value
    )}
  }

  def revenueParser:RowParser[Revenues] = {
    for {
      revenueType <- str(RevenuesEntryRepository.FIELD_REVENUE_TYPE).?
      value <- double(RevenuesEntryRepository.FIELD_VALUE).?
      vacancyRate <- double(RevenuesEntryRepository.FIELD_VACANCY_RATE).?

    } yield {Revenues(
      revenueType = revenueType,
      value = value,
      vacancyRate = vacancyRate
    )}
  }

  def parameterRowsExpenses(property:Property, id:Int): (String,Seq[NamedParameter]) = {
    val values: Seq[Expenses] = property.expenses
    val indexedValues = property.expenses.zipWithIndex

    val rows = indexedValues.map{ case (value, i) =>
      s"({propId_${i}}, {expenseType_${i}}, {value_${i}})"
    }.mkString(",")

    val parameters = indexedValues.flatMap{ case(_value, i) =>
      Seq[NamedParameter](s"propId_${i}" -> id,s"expenseType_${i}" -> _value.expenseType, s"value_${i}" -> _value.value)
    }
    (rows,parameters)

  }

  def parameterRowsRevenues(property:Property, id:Int): (String,Seq[NamedParameter]) = {
          val values: Seq[Revenues] = property.revenues
          val indexedValues2 = property.revenues.zipWithIndex
          val rows = indexedValues2.map{ case (value, i) =>
            s"({propId_${i}}, {revenueType_${i}}, {value_${i}}, {vacancyRate_${i}})"
          }.mkString(",")

          val parameters = indexedValues2.flatMap{ case(_value, i) =>
            Seq[NamedParameter](s"propId_${i}" -> id,s"revenueType_${i}" -> _value.revenueType, s"value_${i}" -> _value.value, s"vacancyRate_${i}" -> _value.vacancyRate)
          }
    (rows,parameters)

  }

}