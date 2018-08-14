package controllers

import javax.inject._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(numberTemplate: views.html.NumberTemplate, cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

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
      "password" -> nonEmptyText(5, 8)
    )
  )

  def index() = Action { implicit request =>
    Ok(numberTemplate("Login Form", loginForm))
  }

  def login: Action[AnyContent] = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithError => {
        BadRequest(numberTemplate("Login Form", formWithError))
      },
      validForm => {
        Ok("welcome "+validForm)
      }
    )

  }
}
