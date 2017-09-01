package unit.helpers

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.model.{GetParameterRequest, GetParameterResult, Parameter}
import helpers.ConfigurationProvider
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import play.api.Configuration

class ConfigurationProviderSpec extends WordSpec with MockitoSugar {

  "ConfigurationProvider" when {

    "there is no secret configuration section in .conf file" should {

      "not make any calls to AWS API" in givenNoSecretConfigSection { (mockAwsClient, fakeAppConfiguration, _) =>
        // when
        new ConfigurationProvider(fakeAppConfiguration, Some(mockAwsClient)).getConfiguration

        // then
        verify(mockAwsClient, never()).getParameter(any())
      }

      "return the configuration without secret override" in givenNoSecretConfigSection { (mockAwsClient, fakeAppConfiguration, nonSecretConfigValue) =>
        // when
        val configuration = new ConfigurationProvider(fakeAppConfiguration, Some(mockAwsClient)).getConfiguration

        // then
        assert(configuration.getString("mongoUri").get == nonSecretConfigValue)
      }

      def givenNoSecretConfigSection(testCode: (AWSSimpleSystemsManagement, Configuration, String) => Any) = {
        val nonSecretConfigValue = "mongodb://some_host/some_database"
        val confWithoutSecret: Map[String, String] = Map("mongoUri" -> nonSecretConfigValue, "application.name" -> "toggle", "hbc.banner" -> "saks", "hbc.env" -> "Dev")
        val fakeAppConfiguration: Configuration = Configuration.from(confWithoutSecret)

        val mockAwsClient = mock[AWSSimpleSystemsManagement]

        testCode(mockAwsClient, fakeAppConfiguration, nonSecretConfigValue)
      }
    }

    "the secret configuration section in .conf file contains a single secret" should {
      "retrieve the secret value from AWS Parameter Store" in givenSingleSecret { (mockAwsClient, expectedRequest, fakeAppConfiguration, _) =>

        // when
        new ConfigurationProvider(fakeAppConfiguration, Some(mockAwsClient))

        // then
        verify(mockAwsClient, atLeastOnce()).getParameter(expectedRequest)
      }

      "override configuration with the secret value retrieved from AWS Parameter Store" in givenSingleSecret { (mockAwsClient, _, fakeAppConfiguration, secretFromParameterStore) =>

        // when
        val configurationProvider = new ConfigurationProvider(fakeAppConfiguration, Some(mockAwsClient))

        val configuration = configurationProvider.getConfiguration

        // then
        assert(configuration.getString("mongoPassword").get == secretFromParameterStore)
      }

      "cache secret overrides in-memory instead of making new calls to the awsClient every time" in givenSingleSecret { (mockAwsClient, expectedRequest, fakeAppConfiguration, _) =>
        // when
        val configurationProvider = new ConfigurationProvider(fakeAppConfiguration, Some(mockAwsClient))

        configurationProvider.getConfiguration
        configurationProvider.getConfiguration

        // then
        verify(mockAwsClient, atMost(1)).getParameter(expectedRequest)
      }

      def givenSingleSecret(testCode: (AWSSimpleSystemsManagement, GetParameterRequest, Configuration, String) => Any) = {
        val secretMap: Map[String, String] = Map("mongoPassword" -> "passwordParameterStoreKey")
        val configMap = Map("secrets" -> secretMap, "mongoPassword" -> "do_not_return_me", "application.name" -> "toggle", "hbc.banner" -> "saks", "hbc.env" -> "Dev")
        val fakeAppConfiguration: Configuration = Configuration.from(configMap)

        val mockAwsClient = mock[AWSSimpleSystemsManagement]
        val expectedRequest = fakeRequest("/toggle/saks/Dev/passwordParameterStoreKey")

        val secretValue = "some_secret_value"
        when(mockAwsClient.getParameter(expectedRequest)).thenReturn(fakeResult(secretValue))

        testCode(mockAwsClient, expectedRequest, fakeAppConfiguration, secretValue)
      }
    }

    "secret configuration section in .conf file contains more than one secret" should {
      "retrieve all secret values from AWS Parameter store" in {
        // given
        val secretMap: Map[String, String] = Map("mongoPassword" -> "passwordParameterStoreKey", "apiToken" -> "apiTokenParameterStoreKey")
        val configMap = Map("secrets" -> secretMap, "mongoPassword" -> "do_not_return_me", "application.name" -> "toggle", "hbc.banner" -> "saks", "hbc.env" -> "Dev")
        val fakeAppConfiguration: Configuration = Configuration.from(configMap)

        val mockAwsClient = mock[AWSSimpleSystemsManagement]
        val expectedRequest = fakeRequest("/toggle/saks/Dev/passwordParameterStoreKey")
        val expectedRequest2 = fakeRequest("/toggle/saks/Dev/apiTokenParameterStoreKey")

        when(mockAwsClient.getParameter(expectedRequest)).thenReturn(fakeResult("irrelevant_secret_value"))
        when(mockAwsClient.getParameter(expectedRequest2)).thenReturn(fakeResult("irrelevant_secret_value"))

        // when
        new ConfigurationProvider(fakeAppConfiguration, Some(mockAwsClient))

        // then
        verify(mockAwsClient).getParameter(expectedRequest)
        verify(mockAwsClient).getParameter(expectedRequest2)
      }
    }

    "awsClient is not defined" should {
      "not fail but return configuration with no overrides" in {
        // given
        val secretMap: Map[String, String] = Map("mongoPassword" -> "passwordParameterStoreKey")
        val configMap = Map("secrets" -> secretMap, "mongoPassword" -> "non_overridden_value", "application.name" -> "toggle", "hbc.banner" -> "saks", "hbc.env" -> "Dev")
        val fakeAppConfiguration: Configuration = Configuration.from(configMap)

        // when
        val configuration = new ConfigurationProvider(fakeAppConfiguration, None).getConfiguration

        // then
        assert(configuration.getString("mongoPassword").get == "non_overridden_value")
        assert(configuration.getString("application.name").get == "toggle")
      }
    }
  }

  def fakeResult(resultValue: String): GetParameterResult = {
    val parameter = new Parameter()
    parameter.setValue(resultValue)
    new GetParameterResult().withParameter(parameter)
  }

  def fakeRequest(param: String) = {
    new GetParameterRequest().withName(param).withWithDecryption(true)
  }

}
