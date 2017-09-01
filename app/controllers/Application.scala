package controllers

import ch.qos.logback.classic.{Level, LoggerContext}
import helpers.ControllerPayloadLike._
import helpers.ControllerTimeoutLike._
import org.slf4j.LoggerFactory
import play.api._
import play.api.mvc._

object Application extends Controller {

  @no.samordnaopptak.apidoc.ApiDoc(doc =
    """
    GET /toggle-service

    DESCRIPTION
      Check to see if toggle-service service is running

    RESULT
      Response

    Request: models.ApiRequestModel
      url: String
      server_received_time: String
      api_version: String
      help: String

    ResponseResult: models.ApiResultModel
      results: String

    Error: models.ApiErrorModel
      data: String
      error: String

    Response: models.ApiModel
      request: Request
      response: ResponseResult
      errors: Array Error
  """)
  def index = Action.async {
    implicit request =>
      timeout {
        val response = "toggle-service is up and running!"
        writeResponseGet(response)
      }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc =

    """
    GET /toggle-service/logLevel/{level}

    DESCRIPTION
      Change the log level of this service

    PARAMETERS
      level: Enum(ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF) String <- The log level you want to set

    RESULT
      Response

  """)
  def changeLogLevelGet(levelString: String) = changeLogLevel(levelString)

  @no.samordnaopptak.apidoc.ApiDoc(doc =

    """
    PUT /toggle-service/logLevel/{level}

    DESCRIPTION
      Change the log level of this service

    PARAMETERS
      level: Enum(ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF) String <- The log level you want to set

    RESULT
      Response

  """)
  def changeLogLevel(levelString: String) = Action.async {
    implicit request =>
      timeout {
        Logger.debug("toggle-service change log level called")

        val level = Level.toLevel(levelString)
        val loggerCtx = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

        loggerCtx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(level)
        loggerCtx.getLogger("application").setLevel(level)
        loggerCtx.getLogger("play").setLevel(level)

        val response = s"Log level changed to $level"
        writeResponseGet(response)
      }
  }

}
