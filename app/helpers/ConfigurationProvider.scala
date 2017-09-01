package helpers

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest
import play.Play
import play.api.Configuration

class ConfigurationProvider(configuration: Configuration, awsClient: Option[AWSSimpleSystemsManagement]) {

  private val application = configuration.getString("application.name").get
  private val environment = configuration.getString("hbc.env").get
  private val banner = configuration.getString("hbc.banner").get

  private val configurationWithSecretsOverride: Configuration = {
    configuration.getConfig("secrets") match {
      case Some(secretsConfigSection) if awsClient.isDefined => configuration ++ createSecretsOverride(secretsConfigSection)
      case _ => configuration
    }
  }

  def getConfiguration: Configuration = configurationWithSecretsOverride

  private def createSecretsOverride(secretsConfigSection: Configuration): Configuration = {
    val secretsMap = secretsConfigSection.keys
      .map(secretKey => secretKey -> getSecretValue(secretsConfigSection, secretKey))
      .toMap[String, String]

    Configuration.from(secretsMap)
  }

  private def getSecretValue(secretsConfig: Configuration, secretKey: String): String = {
    val parameterStoreKey = secretsConfig.getString(secretKey).get

    getParameterStoreValue(s"/$application/$banner/$environment/$parameterStoreKey")
  }

  private def getParameterStoreValue(parameterStoreKey: String): String = {
    val request = new GetParameterRequest()
      .withName(parameterStoreKey)
      .withWithDecryption(true)
    val result = awsClient.get.getParameter(request)
    result.getParameter.getValue
  }

}

object ConfigurationProvider extends ConfigurationProvider(Play.application().configuration().getWrappedConfiguration, AWSSimpleSystemsManagementClientProvider.getClient)
