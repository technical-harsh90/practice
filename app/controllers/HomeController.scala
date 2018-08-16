package controllers

import javax.inject._
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.I18nSupport
import play.api.mvc._
import play.filters.csrf.CSRF
import utils.Constants
import views.html._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport with Constants {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  val loginForm = Form(
    tuple(
      "username" -> nonEmptyText,
      "password" -> text(5, 10)
    )
  )

  def index(): Action[AnyContent] = Action { implicit request =>
    Ok(NumberTemplate(loginForm))
  }

  def login = Action { implicit request =>
    CSRF.getToken.fold(BadRequest(CSRF_TOKEN_NOT_FOUND)) { _ =>
      loginForm.bindFromRequest.fold(
        formWithError => {
          BadRequest(NumberTemplate(formWithError))
        },
        validForm => {
          Redirect(routes.HomeController.index())
            .flashing("username" -> validForm._1)
        }
      )
    }
  }

}
