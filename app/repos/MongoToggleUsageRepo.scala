package repos

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import constants.Constants._
import metrics.StatsDClient._
import models._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import repos.ServiceRepo.usageCollection

import scala.concurrent.Future

object MongoToggleUsageRepo extends ToggleUsageRepo {

  def getAll(): Future[Seq[ToggleUsage]] = {
    Future {
      time("getAll_ToggleUsage_mongoQuery") {
        usageCollection.find().map(grater[ToggleUsage].asObject(_)).toList.sortBy(_.toggle_name)
      }
    }
  }

  def getByToggleName(toggleName: String): Seq[ToggleUsage] = {
    time("getByToggleName_ToggleUsage_mongoQuery") {
      usageCollection.find(TOGGLE_NAME $eq toggleName)
        .map(grater[ToggleUsage].asObject(_)).toList.sortBy(_.user)
    }
  }

  def getByUser(user: String): Seq[ToggleUsage] = {
    time("getByUser_ToggleUsage_mongoQuery") {
      usageCollection.find(USER $eq user)
        .map(grater[ToggleUsage].asObject(_))
        .toList.sortBy(_.toggle_name)
    }
  }

  def upsert(user: String, toggleName: String) = {
    Future {
      time("upsert_ToggleUsage_mongoQuery") {

        val toggleUsage, toggleUsageKey = key(user, toggleName)

        usageCollection.update(toggleUsageKey, toggleUsage, upsert = true)
      }
    }
  }

  def delete(user: String, toggleName: String) = {
    Future {
      time("delete_ToggleUsage_mongoQuery") {
        usageCollection.remove(
          key(user, toggleName)
        )
      }
    }
  }

  def getUsedTogglesNames(): Seq[String] = {
    time("getUsedTogglesNames_ToggleUsage_mongoQuery") {
      usageCollection.distinct(TOGGLE_NAME).map(_.toString)
    }
  }

  private def key(user: String, toggleName: String) = MongoDBObject(
    TOGGLE_NAME -> toggleName,
    USER -> user
  )

}
