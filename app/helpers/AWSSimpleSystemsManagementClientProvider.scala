package helpers

import com.amazonaws.services.simplesystemsmanagement.{AWSSimpleSystemsManagementClientBuilder, AWSSimpleSystemsManagement}
import play.api.Logger

class AWSSimpleSystemsManagementClientProvider() {

  def getClient: Option[AWSSimpleSystemsManagement] = {
    try {
      Some(AWSSimpleSystemsManagementClientBuilder.defaultClient())
    } catch {
      case t: Throwable => {
        Logger.warn("Could not connect to AWS Parameter Store", t)
        None
      }
    }
  }

}

object AWSSimpleSystemsManagementClientProvider extends AWSSimpleSystemsManagementClientProvider