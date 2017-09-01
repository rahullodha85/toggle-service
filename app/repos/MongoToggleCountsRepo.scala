package repos

import com.novus.salat._
import com.novus.salat.global._
import metrics.StatsDClient.time
import models.ToggleCounts

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.Imports
import com.mongodb.casbah.query.dsl.QueryExpressionObject
import models.Fields._
import org.joda.time.DateTime
import repos.ServiceRepo.countsCollection

class MongoToggleCountsRepo extends ToggleCountsRepo {

  def getAll(): Future[Seq[ToggleCounts]] = Future {
    select()
  }

  def getLatest(days: Int): Future[Seq[ToggleCounts]] = Future {
    select(Some(days))
  }

  private def select(maxDays: Option[Int] = None) = {
    time("getAllCounts_mongoQuery") {
      val maxDaysCriterion = maxDays.fold(DBObject())(days => DATE $gte DateTime.now().minusDays(days).toDate)

      countsCollection.find(maxDaysCriterion)
        .map(grater[ToggleCounts].asObject(_)).toSeq
        .sortBy(tc => -tc.date.getMillis)
    }
  }

  def upsert(toggleCounts: ToggleCounts): Unit = {

    time("upsert_ToggleUsage_mongoQuery") {

      countsCollection.update(
        key(toggleCounts),
        toMongoDBObject(toggleCounts),
        upsert = true
      )
    }

  }

  private def toMongoDBObject(toggleCounts: ToggleCounts) = {
    MongoDBObject(
      DATE -> toggleCounts.date.toDate,
      BANNER -> toggleCounts.banner,
      ENV -> toggleCounts.env,
      USED_ACTIVE -> toggleCounts.used_active,
      USED_EXPIRED -> toggleCounts.used_expired,
      UNUSED -> toggleCounts.unused
    )
  }

  private def key(toggleCounts: ToggleCounts) = MongoDBObject(
    DATE -> toggleCounts.date.toDate,
    BANNER -> toggleCounts.banner,
    ENV -> toggleCounts.env
  )

}

object MongoToggleCountsRepo extends MongoToggleCountsRepo
