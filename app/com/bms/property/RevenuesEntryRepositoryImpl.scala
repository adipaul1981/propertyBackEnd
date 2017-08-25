package com.bms.property

import javax.inject.Inject

import anorm.SqlParser.{double, int, str}
import anorm.{BatchSql, NamedParameter, RowParser, SQL}
import play.api.db.Database
import play.db.NamedDatabase

import scala.concurrent.{ExecutionContext, Future, blocking}

class RevenuesEntryRepositoryImpl @Inject() (
                                              @NamedDatabase("default") db: Database
                                            )(implicit ec: ExecutionContext) extends RevenuesEntryRepository {

  override def save(revenues: Seq[Revenues], id:Int): Future[Unit] = Future(blocking {
    db.withConnection { implicit c =>
//      val sql =
//        s"""INSERT INTO ${RevenuesEntryRepository.TABLE_NAME} (${RevenuesEntryRepository.ALL_FIELDS}) VALUES ${RevenuesEntryRepositoryImpl.values(revenues)}"""
//
//      SQL(sql)
//        .on(RevenuesEntryRepositoryImpl.generateOn(revenues, id): _*)
//        .executeInsert()
      val allFields = s"${RevenuesEntryRepository.FIELD_PROPERTY_ID}, ${RevenuesEntryRepository.FIELD_REVENUE_TYPE},${RevenuesEntryRepository.FIELD_VALUE},${RevenuesEntryRepository.FIELD_VACANCY_RATE}"
      val allValues = s"{${RevenuesEntryRepository.FIELD_PROPERTY_ID}},{${RevenuesEntryRepository.FIELD_REVENUE_TYPE}}," +
        s"{${RevenuesEntryRepository.FIELD_VALUE}},{${RevenuesEntryRepository.FIELD_VACANCY_RATE}}"
      println(allFields)
      println(allValues)
      val batch = BatchSql(
        s"INSERT INTO ${RevenuesEntryRepository.TABLE_NAME}(${allFields}) VALUES(${allValues})",
        revenues.map(r => Seq[NamedParameter](s"${RevenuesEntryRepository.FIELD_PROPERTY_ID}" -> id,
          s"${RevenuesEntryRepository.FIELD_REVENUE_TYPE}" -> r.revenueType,
          s"${RevenuesEntryRepository.FIELD_VALUE}" -> r.value,
          s"${RevenuesEntryRepository.FIELD_VACANCY_RATE}" -> r.vacancyRate))
      )

      val res: Array[Int] = batch.execute() // array of update count

    }
  })

//  UPDATE table_name
//    SET column1 = value1, column2 = value2, ...
//  WHERE condition;

//  override def getRevenueByPropertyExpenseID(id: Int): Option[Revenues] =  {
//    db.withConnection { implicit c =>
//      val sql =
//        s"""|SELECT * FROM ${RevenuesEntryRepository.TABLE_NAME}
//            |           WHERE ${RevenuesEntryRepository.FIELD_PROPERTY_EXPENSES_ID} = {${RevenuesEntryRepository.FIELD_PROPERTY_EXPENSES_ID}}
//           """.stripMargin
//      SQL(sql)
//        .on(s"${RevenuesEntryRepository.FIELD_PROPERTY_EXPENSES_ID}" -> id)
//        .as(RevenuesEntryRepositoryImpl.expensesParser.*)
//        .headOption
//    }
//  }
}


object RevenuesEntryRepositoryImpl {

      def generateOn(f: Seq[Revenues], id:Int): Seq[NamedParameter] =
          Seq(
            f.zipWithIndex.map({case(v,i) => NamedParameter(s"${RevenuesEntryRepository.FIELD_PROPERTY_ID}_${i}",id)}),
            f.zipWithIndex.flatMap({case(v,i) => v.revenueType.map(x => NamedParameter(s"${RevenuesEntryRepository.FIELD_REVENUE_TYPE}_${i}",x))}),
            f.zipWithIndex.flatMap({case(v,i) => v.value.map(x => NamedParameter(s"${RevenuesEntryRepository.FIELD_VALUE}_${i}",x))}),
            f.zipWithIndex.flatMap({case(v,i) => v.vacancyRate.map(x => NamedParameter(s"${RevenuesEntryRepository.FIELD_VACANCY_RATE}_${i}",x))})
          ).flatten




      def values(f: Seq[Revenues]) =
              f.zipWithIndex.map({case(v,i) => s"({${RevenuesEntryRepository.FIELD_PROPERTY_ID}_${i}},{${RevenuesEntryRepository.FIELD_REVENUE_TYPE}_${i}}" +
                s",{${RevenuesEntryRepository.FIELD_VALUE}_${i}},{${RevenuesEntryRepository.FIELD_VACANCY_RATE}_${i}})"}).mkString(",")












//  def expensesParser: RowParser[Revenues] = {
//    for {
//      id <- int(RevenuesEntryRepository.FIELD_ID)
//      property_expenses_id <- int(RevenuesEntryRepository.FIELD_PROPERTY_EXPENSES_ID)
//      residential <- double(RevenuesEntryRepository.FIELD_RESIDENTIAL).?
//      commercial <- double(RevenuesEntryRepository.FIELD_COMMERCIAL).?
//      parking_garages <- double(RevenuesEntryRepository.FIELD_PARKING_GARAGE).?
//      others <- double(RevenuesEntryRepository.FIELD_OTHERS).?
//      vacancy_rate_residential <- double(RevenuesEntryRepository.FIELD_VR_RESIDENTIAL).?
//      vacancy_rate_commercial <- double(RevenuesEntryRepository.FIELD_VR_COMMERCIAL).?
//      vacancy_rate_parking <- double(RevenuesEntryRepository.FIELD_VR_PARKING).?
//      vacancy_rate_others <- double(RevenuesEntryRepository.FIELD_VR_OTHERS).?
//    } yield {
//      Revenues(
//        id = id,
//        property_expenses_id = property_expenses_id,
//        residential = residential,
//        commercial = commercial,
//        parking_garages = parking_garages,
//        others = others,
//        vacancy_rate_residential = vacancy_rate_residential,
//        vacancy_rate_commercial = vacancy_rate_commercial,
//        vacancy_rate_parking = vacancy_rate_parking,
//        vacancy_rate_others = vacancy_rate_others
//      )
//    }
//  }

}

