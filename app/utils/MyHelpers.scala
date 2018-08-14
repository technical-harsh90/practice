package utils

object MyHelpers {
  import views.html.helper.FieldConstructor
  implicit val myFields: FieldConstructor = FieldConstructor(views.html.constructor.f)
}
