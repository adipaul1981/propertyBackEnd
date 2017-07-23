//package controllers
//
//import javax.inject.Inject
//
//import play.api.mvc._
//import play.api.libs.json._
//import play.api.libs.functional.syntax._
//import com.bms.property.{Revenues, RevenuesEntryRepository}
//import play.api.libs.json.{JsValue, Json}
//import play.api.mvc.{Action, Controller}
//
//import scala.concurrent.{ExecutionContext, Future}
//import scala.util.Try
//
//class RevenuesController @Inject()(
//                                    revenueEntryRepo: RevenuesEntryRepository
//                                  ) (implicit ec: ExecutionContext) extends Controller {
//
//  def saveRevenues(): Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
//    val entries = (request.body).asOpt[Revenues]
//    val test = entries map(_.asInstanceOf[Revenues]) getOrElse(null)  //TODO
//    println(test)
//    if (entries.nonEmpty) {
//      revenueEntryRepo.save(test)
//      val json = Json.toJson(test)
//      Future.successful(Created(json))
//
//    } else {
//      Future.successful(BadRequest)
//    }
//  }
//
//  def getRevenuesByPropertyID() = Action {implicit request =>
//    val id = request.queryString("ID").head
//    val expenses = revenueEntryRepo.getRevenueByPropertyExpenseID(id.toInt)
//    println(expenses)
//    val json = Json.toJson(expenses)
//    println(json)
//    Ok(json)
//  }
//
//
//}
//
//object RevenuesController extends Controller
//{
//
//  def save = {}
//
//
//
//}
//
