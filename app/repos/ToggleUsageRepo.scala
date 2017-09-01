package repos

import models.ToggleUsage

import scala.concurrent.Future

trait ToggleUsageRepo {

  def getAll(): Future[Seq[ToggleUsage]]
  def getByUser(user: String): Seq[ToggleUsage]
  def getByToggleName(toggleName: String): Seq[ToggleUsage]
  def getUsedTogglesNames(): Seq[String]

  def upsert(user: String, toggleName: String): Unit
  def delete(user: String, toggleName: String): Unit
}
