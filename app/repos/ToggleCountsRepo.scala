package repos

import models.ToggleCounts

import scala.concurrent.Future

trait ToggleCountsRepo {
  def getAll(): Future[Seq[ToggleCounts]]
  def getLatest(days: Int): Future[Seq[ToggleCounts]]
  def upsert(toggleCount: ToggleCounts): Unit
}
