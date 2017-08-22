package controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import com.bms.property.{Property, Revenues, RevenuesEntryRepository}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class RevenuesController @Inject()(
                                    revenueEntryRepo: RevenuesEntryRepository
                                  ) (implicit ec: ExecutionContext) extends Controller {

  def saveRevenues(id : Int): Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
//    val entries = (request.body).asOpt[Seq[Revenues]]
    val entries = (request.body).asOpt[Seq[Revenues]] match {
      case Some(seqRevenues) => seqRevenues
      case None => Nil
  }

    if (entries.nonEmpty) {
      revenueEntryRepo.save(entries, id)
      val json = Json.toJson(entries)
      Future.successful(Created(json))

    } else {
      println("OUps")
      Future.successful(BadRequest)
    }
  }

//  def getRevenuesByPropertyID() = Action {implicit request =>
//    val id = request.queryString("ID").head
//    val expenses = revenueEntryRepo.getRevenueByPropertyExpenseID(id.toInt)
//    println(expenses)
//    val json = Json.toJson(expenses)
//    println(json)
//    Ok(json)
//  }


}

object RevenuesController extends Controller
{

  def save = {}



}

