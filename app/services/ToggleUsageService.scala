package services

import constants.Constants
import controllers.ToggleRepo
import models._
import repos._
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}
import constants.Constants.EXPIRATION_WINDOW_DAYS
import helpers.ConfigurationProvider
import play.api.Configuration

object ToggleUsageService extends ToggleUsageService(MongoToggleUsageRepo, MongoTogglesRepo, MongoToggleCountsRepo, ConfigurationProvider.getConfiguration)

class ToggleUsageService(toggleUsagesRepo: ToggleUsageRepo, toggleRepo: ToggleRepo, toggleCountsRepo: ToggleCountsRepo, configuration: Configuration) {

  def register(toggleRegistration: ToggleRegistration)(implicit executionContext: ExecutionContext): Future[Boolean] = {
    Future.sequence {
      toggleRegistration.toggles.map { toggleDefinition =>
        registerToggle(toggleRegistration, toggleDefinition)
      }
    }.flatMap { toggleRegistryResults =>
      deleteUnregisteredToggles(toggleRegistration, toggleRegistryResults)
    }
  }

  private def registerToggle(toggleRegistration: ToggleRegistration, toggleDefinition: ToggleDefinition)(implicit executionContext: ExecutionContext): Future[Boolean] = {
    createToggleIfNonexistent(toggleDefinition) flatMap {
      case true => Future.successful {
        toggleUsagesRepo.upsert(toggleRegistration.user, toggleDefinition.toggle_name)
        true
      }
      case _ => Future(Constants.TOGGLE_REGISTRATION_FAILURE)
    }
  }

  private def deleteUnregisteredToggles(toggleRegistration: ToggleRegistration, toggleRegistryResults: Seq[Boolean])(implicit executionContext: ExecutionContext): Future[Boolean] = {
    toggleRegistryResults contains Constants.TOGGLE_REGISTRATION_FAILURE match {
      case false => {
        val unregisteredToggleNames: List[String] = findUnregisteredToggles(toggleRegistration)
        unregisteredToggleNames.foreach { unregisteredToggleName => toggleUsagesRepo.delete(toggleRegistration.user, unregisteredToggleName) }
        Future(Constants.TOGGLE_REGISTRATION_SUCCESS)
      }
      case _ => Future(Constants.TOGGLE_REGISTRATION_FAILURE)
    }
  }

  private def findUnregisteredToggles(toggleRegistration: ToggleRegistration): List[String] = {
    val togglesUsedByUser = toggleUsagesRepo
      .getByUser(toggleRegistration.user)
      .map(u => u.toggle_name).toList

    val togglesBeingRegistered = toggleRegistration.toggles.map(td => td.toggle_name).toList

    togglesUsedByUser.diff(togglesBeingRegistered)
  }

  private def createToggleIfNonexistent(toggleDefinition: ToggleDefinition)(implicit executionContext: ExecutionContext): Future[Boolean] = {
    toggleRepo.get(toggleDefinition.toggle_name) match {
      case None => createNewToggleWithDefaultState(toggleDefinition)
      case _    => Future.successful(true);
    }
  }

  private def createNewToggleWithDefaultState(toggleDefinition: ToggleDefinition)(implicit executionContext: ExecutionContext): Future[Boolean] = {
    val toggleUpdate = ToggleUpdateBuilder().fromNew(toggleDefinition).build()
    toggleRepo.create(toggleUpdate)
  }

  def findUnusedToggles()(implicit executionContext: ExecutionContext): Future[Seq[Toggle]] = {
    val allToggles = toggleRepo.getAll()
    val usedTogglesNames = toggleUsagesRepo.getUsedTogglesNames().toIndexedSeq
    allToggles.map(
      _.filter { t => !usedTogglesNames.contains(t.toggle_name) }
    )
  }

  def deleteUnusedToggles(delete: Boolean = false)(implicit executionContext: ExecutionContext): Future[Seq[Toggle]] = {
    findUnusedToggles().map { toggles =>
      {
        toggles.foreach(t => toggleRepo.delete(t.toggle_name))
        toggles
      }
    }
  }

  def findCounts(maxDays: Int): Future[Seq[ToggleCounts]] = {
    if (maxDays == 0) toggleCountsRepo.getAll() else toggleCountsRepo.getLatest(maxDays)
  }

  def getCurrentCounts()(implicit executionContext: ExecutionContext): Future[ToggleCounts] = {
    calculateCurrentCounts
  }

  def recordCurrentCounts()(implicit executionContext: ExecutionContext): Future[ToggleCounts] = {
    calculateCurrentCounts
      .map { counts =>
        toggleCountsRepo.upsert(counts)
        counts
      }
  }

  private def calculateCurrentCounts()(implicit executionContext: ExecutionContext) = {
    val allToggles = toggleRepo.getAll()
    val usedTogglesNames = toggleUsagesRepo.getUsedTogglesNames().toIndexedSeq

    allToggles.map { toggles =>

      val expirationDate = DateTime.now().minusDays(EXPIRATION_WINDOW_DAYS).getMillis

      val (allUsedToggles, unusedToggles) = deduplicateByName(toggles).partition(toggle => usedTogglesNames.contains(toggle.toggle_name))
      val (usedExpired, usedActive) = allUsedToggles.partition(toggle => toggle.modified_timestamp.isBefore(expirationDate))

      ToggleCounts(
        date = DateTime.now.withTimeAtStartOfDay,
        banner = configuration.getString(Constants.HBC_BANNER).get.toLowerCase,
        env = configuration.getString(Constants.HBC_ENV).get.toLowerCase,
        used_active = usedActive.size,
        unused = unusedToggles.size,
        used_expired = usedExpired.size
      )
    }

  }

  private def deduplicateByName(toggles: Seq[Toggle]) = {
    toggles.groupBy(_.toggle_name).mapValues(_.head).values
  }
}