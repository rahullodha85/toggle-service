package helpers

import constants.Constants
import jp.t2v.lab.play2.auth.AuthConfig
import models.HbcUser
import scala.reflect._
import scala.concurrent.{Future, ExecutionContext}
import play.api.mvc.RequestHeader
import play.api.mvc.Results._
import controllers.{ToggleAdmin, routes}

trait AuthConfigImpl extends AuthConfig {

  type Id = String

  type User = HbcUser

  type Authority = String

  val idTag = classTag[Id]

  val sessionTimeoutInSeconds = 3600

  def resolveUser(email: Id)(implicit context: ExecutionContext) = Future.successful(Some(HbcUser(email, "", Constants.ROLE_USER)))

  def logoutSucceeded(request: RequestHeader)(implicit context: ExecutionContext) = Future.successful(Redirect(routes.ToggleAdmin.login).flashing(
    "success" -> "You've been logged out"
  ))

  def authenticationFailed(request: RequestHeader)(implicit context: ExecutionContext) =
    Future.successful(Redirect(routes.ToggleAdmin.login).withSession("access_uri" -> request.uri))

  def loginSucceeded(request: RequestHeader)(implicit context: ExecutionContext) = {
    val uri = request.session.get("access_uri").getOrElse(routes.ToggleAdmin.togglesList)
    Future.successful(Redirect(uri.toString).withSession(request.session - "access_uri"))
  }

  def authorizationFailed(request: RequestHeader)(implicit context: ExecutionContext) = Future.successful(Forbidden("Not Permitted"))

  def authorize(user: User, authority: ToggleAdmin.Authority)(implicit context: ExecutionContext): Future[Boolean] = {
    Future.successful(user.access == authority)
  }
}
