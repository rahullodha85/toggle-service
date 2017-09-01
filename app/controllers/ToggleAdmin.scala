package controllers

import constants.Constants
import helpers._
import models.HbcUser
import play.api.data.Form
import play.api.data.Forms._
import views.html
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.language.higherKinds

import jp.t2v.lab.play2.auth.{LoginLogout, AuthElement}

/**
 * Created by samirmes on 5/1/15.
 */
class ToggleAdminLike
    extends Controller
    with AuthElement
    with LoginLogout
    with AuthConfigImpl {

  ////////////////////////
  //      Login         //
  ////////////////////////

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(validateAuthentication)(_.map(hbcUser => (hbcUser.password, ""))).verifying("Password incorrect", result => result.isDefined)
  )

  def validateAuthentication(username: String, password: String): Option[HbcUser] = {
    password match {
      case "Saks" => Some(HbcUser(username, password, Constants.ROLE_USER))
      case _      => None
    }
  }

  def login = Action.async { implicit request =>
    Future.successful(Ok(views.html.login(loginForm)))
  }

  def logout = Action.async { implicit request =>
    gotoLogoutSucceeded
  }

  def authenticate = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.login(formWithErrors))),
      { user => gotoLoginSucceeded(user.get.username) }
    )
  }

  ////////////////////////////////
  //      APP NAVIGATION        //
  ////////////////////////////////

  def togglesList = AsyncStack(AuthorityKey -> Constants.ROLE_USER) { implicit request =>
    Future.successful(Ok(views.html.toggles(loggedIn)))
  }

  //GET
  def editTogglesView = AsyncStack(AuthorityKey -> Constants.ROLE_USER) { implicit request =>
    val toggleName = request.queryString("featureName").head // no point in doing anything if we are given a bum name!!
    Future.successful(Ok(views.html.add_edit_toggle(loggedIn, queryParamSafe(toggleName))))
  }

  // POST
  def editToggles = AsyncStack(AuthorityKey -> Constants.ROLE_USER) { implicit request =>
    val toggleName = request.queryString("featureName").head
    Future.successful(Ok(views.html.add_edit_toggle(loggedIn, queryParamSafe(toggleName))))
  }

  def getHistory(toggleName: String) = AsyncStack(AuthorityKey -> Constants.ROLE_USER) { implicit request =>
    Future.successful(Ok(views.html.history(toggleName)))
  }

  def queryParamSafe(s: String): String = s.replaceAll(" ", "%20")
}

object ToggleAdmin extends ToggleAdminLike
