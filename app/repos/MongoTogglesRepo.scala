package repos

import play.api.Logger
import metrics.StatsDClient._
import constants.Constants._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future
import models._
import org.joda.time.DateTime
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._

// just wrap results in futures for expected future (!) api compatibility ...
// since one day we'll try and move off casbah onto who knows but something really async, probably

class MongoTogglesRepo extends controllers.ToggleRepo {

  val togglesCollection = ServiceRepo.mongoCollection

  def getAll(): Future[Seq[Toggle]] = Future {
    time("findAllToggles_mongoQuery") {
      togglesCollection.find()
        .map(grater[Toggle].asObject(_)).toList.sortBy(_.toggle_name)
    }
  }

  def get(toggleName: String): Option[Toggle] = {
    time("findByNameToggle_mongoQuery") {
      togglesCollection.findOne(TOGGLE_NAME $eq toggleName)
        .map(grater[Toggle].asObject(_))
    }
  }

  // unique index on toggle name so throws exception if we try and add a duplicate
  def create(t: ToggleUpdate): Future[Boolean] = {
    Logger.info("CREATING NEW TOGGLE: " + t)
    time("createToggle_mongoquery") {
      val now = new DateTime().toDate
      val tog = MongoDBObject(
        TOGGLE_NAME -> t.toggle_name.toUpperCase,
        TOGGLE_STATE -> t.toggle_state.getOrElse("false"),
        CREATED_TIMESTAMP -> now,
        MODIFIED_TIMESTAMP -> now,
        DESCRIPTION -> t.description.getOrElse(""),
        TOGGLE_TYPE -> t.toggle_type,
        MODIFIED_BY -> t.modified_by
      )
      val result = togglesCollection.insert(tog)
      recordAction(result, t.toggle_name, "toggle created")
    } recover {
      // slightly more descriptive message
      case x: com.mongodb.DuplicateKeyException =>
        throw new Exception(s"Toggle with name ${t.toggle_name.toUpperCase} already exists")
    }
  }

  // mainly for turning it on/off
  // Fields to be edited: type, description, on/off
  def update(t: ToggleUpdate): Future[Boolean] = {
    time("updateToggle_mongoquery") { // may as well just update the fields, it doesn't seem worth bothering with the some kind of check to see what changed...
      val updates: Map[String, Any] = Map(
        DESCRIPTION -> t.description.getOrElse(""),
        TOGGLE_STATE -> t.toggle_state.getOrElse(false),
        TOGGLE_TYPE -> t.toggle_type,
        MODIFIED_TIMESTAMP -> new DateTime().toDate,
        MODIFIED_BY -> t.modified_by
      )

      val result = togglesCollection.update(
        MongoDBObject(TOGGLE_NAME -> t.toggle_name.toUpperCase),
        MongoDBObject("$set" -> updates)
      )

      recordAction(result, t.toggle_name.toUpperCase, "toggle updated")
    }
  }

  def delete(toggleName: String): Future[Boolean] = {
    time("deleteToggle_mongoquery") {
      get(toggleName).map { toggle =>
        val result = togglesCollection.remove(MongoDBObject(TOGGLE_NAME -> toggleName))
        recordAction(result, toggle.copy(modified_timestamp = new DateTime()), "toggle removed")
      } getOrElse Future.successful {
        Logger.error(s"MongoTogglesRepo.delete(): Unable to find toggle '$toggleName'!")
        false
      }
    }
  }

  def recordAction(result: com.mongodb.WriteResult, toggleName: String, action: String): Future[Boolean] = {
    // just dispatch the history entry, if it doesn't work user isn't going to care much...
    get(toggleName).map { toggle =>
      recordAction(result, toggle, action)
    } getOrElse Future.successful {
      Logger.error(s"MongoTogglesRepo.recordAction(): Unable to find toggle '$toggleName'!")
      false
    }

  }

  def recordAction(result: com.mongodb.WriteResult, toggle: Toggle, action: String): Future[Boolean] = {
    // just dispatch the history entry, if it doesn't work user isn't going to care much...
    MongoTogglesHistoryRepo.create(toggle, action)
  }

}

object MongoTogglesRepo extends MongoTogglesRepo