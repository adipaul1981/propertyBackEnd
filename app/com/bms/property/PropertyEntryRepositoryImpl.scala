package com.bms.property

import javax.inject.Inject

import anorm.SqlParser.double
import anorm._
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}


class PropertyEntryRepositoryImpl @Inject() (
  @NamedDatabase("default") db: Database
)(implicit ec: ExecutionContext) extends PropertyEntryRepository {

  override def save(property: Property): Future[Unit]= Future(blocking {
    db.withConnection{implicit c =>
      val sql =
        s"""INSERT INTO ${PropertyEntryRepository.TABLE_NAME} (${PropertyEntryRepository.ALL_FIELDS}) VALUES ({mls},{price},{muneval}
           ,{onehalf},{twohalf},{threehalf},{fourhalf},{fivehalf},{sixhalf},{street_no},{street},{city},{province},{country})""".stripMargin
      println("TOTO")
      println(sql)
      SQL(sql)
        .on('mls -> property.mls_no,'price -> property.price, 'muneval -> property.muneval,
          'onehalf -> property.no1half,'twohalf -> property.no2half,'threehalf -> property.no3half,'fourhalf -> property.no4half,
          'fivehalf -> property.no5half,'sixhalf -> property.no6half,'street_no -> property.address.streetNum,'street -> property.address.street, 'city -> property.address.city,
          'province -> property.address.province, 'country -> property.address.country)
          .executeInsert()
    }
  })
  override def delete(id: Int): Future[Unit]= Future(blocking {
    db.withConnection{implicit c =>
      val sql =
        s"""DELETE FROM ${PropertyEntryRepository.TABLE_NAME}  WHERE (${PropertyEntryRepository.FIELD_ID}={property_id})""".stripMargin
      SQL(sql)
        .on('property_id -> id)
        .executeInsert()
    }
  })

  //TODO Construct the "WHERE" String (make  get really generic)
  override def get(str: String): Option[Property] = {

    db.withConnection{implicit c =>
      val sql =
        s"""|SELECT * FROM ${PropertyEntryRepository.TABLE_NAME}
            |           WHERE ${PropertyEntryRepository.FIELD_MLS} = {mls}
           """.stripMargin
      SQL(sql)
        .on('mls -> str)
        .as(PropertyEntryRepositoryImpl.propertyParser.*)
        .headOption
    }
  }

  override def getList : List[Property] = {

    db.withConnection{implicit c =>
      val sql =

        s"""|SELECT * FROM ${PropertyEntryRepository.TABLE_NAME} LIMIT 2000
           """.stripMargin
      SQL(sql)
        .as(PropertyEntryRepositoryImpl.propertyParser.*)
    }
  }
}

object PropertyEntryRepositoryImpl{
  import anorm.SqlParser.{ str, int,double }

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
    address = address
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


}