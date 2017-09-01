package repos

import play.api.Logger
import metrics.StatsDClient._

import com.typesafe.config.Config
import constants.Constants._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import models._
import org.joda.time.DateTime

import com.mongodb.{ReadPreference, MongoClientOptions}
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._

object MongoTogglesHistoryRepo extends controllers.ToggleHistoryService {

  val coll = ServiceRepo.historyCollection

  // reverse chron
  implicit val dateTimeOrdering = new Ordering[DateTime] {
    def compare(x: DateTime, y: DateTime): Int =
      if (x isBefore y)
        1
      else if (x isAfter y)
        -1
      else
        0
  }

  def getAllHistory(): Future[Seq[ToggleHistory]] = Future {
    time("findAllTogglesHistory_mongoQuery") {
      coll.find()
        .map(grater[ToggleHistory].asObject(_)).toList.sortBy(t => (t.toggle_name, t.modified_timestamp)) // ??
    }
  }

  def getHistory(toggleName: String): Future[Seq[ToggleHistory]] = Future {
    time("findByNameToggleHistory_mongoQuery") {
      coll.find(TOGGLE_NAME $eq toggleName)
        .map(grater[ToggleHistory].asObject(_)).toList.sortBy(_.modified_timestamp)
    }
  }

  def create(t: Toggle, action: String): Future[Boolean] = Future {
    Logger.info(s"CREATING NEW TOGGLE HISTORY ENTRY: $action on toggle $t")
    time("createToggleHistory_mongoquery") {
      val now = new DateTime().toDate
      val tog = MongoDBObject(
        TOGGLE_NAME -> t.toggle_name.toUpperCase,
        TOGGLE_STATE -> t.toggle_state,
        MODIFIED_TIMESTAMP -> t.modified_timestamp.toDate,
        DESCRIPTION -> t.description,
        TOGGLE_TYPE -> t.toggle_type,
        MODIFIED_BY -> t.modified_by,
        ACTION -> action
      )
      coll.insert(tog)
      true
    }
  }

}