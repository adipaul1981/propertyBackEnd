package controllers


import javax.inject.{Inject, Singleton}

import com.bms.property._
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.sun.xml.internal.ws.api.message.MessageWritable
import play.api.http.Writeable
import play.api.mvc.{Action, Controller}
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try








@Singleton
class ProspectPropertyController @Inject()(
                                            propertyEntryRepo: PropertyEntryRepository
                                          ) (implicit ec: ExecutionContext) extends Controller {

  def newPropertyView = Action {
    Ok(views.html.main("Add New com.bms.property.Property")(views.html.addproperty()))
  }


  def create: Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
    val entries = (request.body).asOpt[Property]

    val property = entries map(_.asInstanceOf[Property]) getOrElse(null)  //TODO
    if (entries.nonEmpty) {
      propertyEntryRepo.saveProperty(property)
      property.expenses match {
        case Some(expenses) => propertyEntryRepo.saveExpenses(expenses,property.id)
        case None => println("No Expenses")
      }
      property.revenues match {
        case Some(revenues) => propertyEntryRepo.saveRevenues(revenues,property.id)
        case None => println("No Revenues")
      }
      val json = Json.toJson(property)
      Future.successful(Created(json))

    } else {
      Future.successful(BadRequest)
    }
  }

  def getPropertyByMLS(mls:String)= Action {

    val property = propertyEntryRepo.get(mls)
    val json = Json.toJson(property)
    println("GET Property")
    Ok(json)
  }


//  def getAll()= Action {
//
//    val propertyList = propertyEntryRepo.getList
////    val json = Json.toJson(property)
//    Ok(views.html.main("list")(views.html.propertyList(propertyList)))
//  }

//  def deleteProperty = Action {implicit request =>   //.async(parse.tolerantJson)
//    val id = request.getQueryString("Id").getOrElse(null).toInt
//    val id2 =Try { (request.getQueryString("Id"))}.getOrElse(null)
//    if (id2 != null ) {
//      propertyEntryRepo.delete(id)
//      Future.successful(Created)
//
//    } else {
//      Future.successful(BadRequest)
//    }
//    Ok
//  }


  //----------------

  trait JsonValueReader[A] {
    def read(n: JsDefined): A
  }



}
