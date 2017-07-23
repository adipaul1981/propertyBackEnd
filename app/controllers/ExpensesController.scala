//package controllers
//
//import javax.inject.Inject
//
//import play.api.mvc._
//import play.api.libs.json._
//import play.api.libs.functional.syntax._
//import com.bms.property.{Expenses, ExpensesEntryRepository}
//import controllers.ExpensesController.Ok
//import play.api.libs.json.{JsValue, Json}
//import play.api.mvc.{Action, Controller}
//
//import scala.concurrent.{ExecutionContext, Future}
//import scala.util.Try
//
//class ExpensesController @Inject()(
//                                    expensesEntryRepo: ExpensesEntryRepository
//                                  ) (implicit ec: ExecutionContext) extends Controller {
//
//    def saveExpenses(): Action[JsValue] = Action.async(parse.tolerantJson) { implicit request =>
//        val entries = (request.body).asOpt[Expenses]
//        val test = entries map(_.asInstanceOf[Expenses]) getOrElse(null)  //TODO
//        println(test)
//        if (entries.nonEmpty) {
//          expensesEntryRepo.save(test)
//          val json = Json.toJson(test)
//          Future.successful(Created(json))
//
//        } else {
//          Future.successful(BadRequest)
//        }
//    }
//
////    def getExpensesByPropertyID() = Action(parse.json) {implicit request =>
////      val entries = (request.body).asOpt[Int]
////      println(entries)
////
////      val expenses = expensesEntryRepo.getExpenseByPropertyExpenseID(19)
////      Ok
////    }
//
//    def getExpensesByPropertyID() = Action {implicit request =>
//      val id = request.queryString("ID").head
//      val expenses = expensesEntryRepo.getExpenseByPropertyExpenseID(id.toInt)
//      println(expenses)
//      val json = Json.toJson(expenses)
//      println(json)
//      Ok(json)
//      }
//
//
//}
//
//  object ExpensesController extends Controller
//  {
//
//def save = {}
//
//
//
//  }

