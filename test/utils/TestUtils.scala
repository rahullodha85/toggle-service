package utils

import globals.GlobalServiceSettings
import helpers.ConfigurationProvider
import play.api.{Application, Configuration}
import repos.ServiceRepo

object TestUtils extends TestUtils(ConfigurationProvider.getConfiguration)

class TestUtils(configuration: Configuration) {
  val versionCtx = configuration.getString("application.context").get
}

object TestGlobal extends GlobalServiceSettings {
  override def onStart(app: Application): Unit = {
    ServiceRepo.createServiceRepoClient
  }
}
