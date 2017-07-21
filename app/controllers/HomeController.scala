package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.Html;


@Singleton
class HomeController @Inject() extends Controller {

  def main = Action {
    Ok(views.html.main("Home")(views.html.mainBody()))
  }

}
