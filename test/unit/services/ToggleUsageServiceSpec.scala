package unit.services

import constants.Constants
import controllers.ToggleRepo
import models._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.mockito.ArgumentCaptor
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import play.api.Configuration
import repos.{ToggleCountsRepo, ToggleUsageRepo}
import services.ToggleUsageService
import unit.common.ToggleBuilder

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class ToggleUsageServiceSpec extends WordSpec with MockitoSugar with ScalaFutures {
  val SAMPLE_USER_SERVICE = "sample-service"
  val SAMPLE_ENV = "sample-env"
  val SAMPLE_BANNER = "sample-banner"

  ".register" when {

    "called with one toggle" when {
      val TOGGLE_IN_USE: String = "TOGGLE_IN_USE"

      "that toggle is brand new and hasn't been used before" should {

        def withBrandNewToggle(testCode: (ToggleUsageRepo, ToggleRepo, ToggleRegistration) => Any) = {
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockTogglesRepo = mock[ToggleRepo]

          doReturn(Seq.empty).when(mockToggleUsagesRepo).getByUser(anyString())

          // given
          val toggleDefinition = ToggleDefinition(toggle_name = "BRAND_NEW_TOGGLE", description = "BRAND_NEW_TOGGLE DESCRIPTION", toggle_type = "Temporary")
          val toggleRegistration = ToggleRegistration(user = SAMPLE_USER_SERVICE, toggles = Seq(toggleDefinition))
          setupNonexistentToggle(mockTogglesRepo, toggleDefinition)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition)
          when(mockTogglesRepo.create(any())).thenReturn(Future(true))

          testCode(mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration)
        }

        "create the toggle with a default value (false)" in withBrandNewToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          toggleUsagesService.register(toggleRegistration)

          val toggleUpdate = ToggleUpdate(toggle_name = "BRAND_NEW_TOGGLE", toggle_state = Some(false), description = Some("BRAND_NEW_TOGGLE DESCRIPTION"), toggle_type = Some("Temporary"), modified_by = "deployment")
          verify(mockTogglesRepo).create(toggleUpdate)
        }

        "record new usage of that toggle" in withBrandNewToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          toggleUsagesService.register(toggleRegistration)

          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).upsert(SAMPLE_USER_SERVICE, "BRAND_NEW_TOGGLE")
        }

        "return a success message" in withBrandNewToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          toggleUsagesService.register(toggleRegistration).map {
            result: Boolean => assert(result)
          }
        }
      }

      "that toggle is not brand new but hasn't been used before" should {

        def withExistingUnusedToggle(testCode: (ToggleUsageRepo, ToggleRepo, ToggleRegistration) => Any) = {
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockTogglesRepo = mock[ToggleRepo]
          doReturn(Seq.empty).when(mockToggleUsagesRepo).getByUser(anyString())

          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)
          // given
          val toggleDefinition = ToggleDefinition(toggle_name = "EXISTING_TOGGLE", description = "EXISTING_TOGGLE DESCRIPTION", toggle_type = "Temporary")
          val toggleRegistration = ToggleRegistration(user = SAMPLE_USER_SERVICE, toggles = Seq(toggleDefinition))
          setupExistingToggle(mockTogglesRepo, toggleDefinition)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition)
          when(mockTogglesRepo.get(toggleDefinition.toggle_name)).thenReturn(any())

          // when
          toggleUsagesService.register(toggleRegistration)

          testCode(mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration)

        }

        "not add another toggle" in withExistingUnusedToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          verify(mockTogglesRepo, never()).create(any())
        }

        "record new usage of that toggle" in withExistingUnusedToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).upsert(SAMPLE_USER_SERVICE, "EXISTING_TOGGLE")
        }
      }

      "that toggle has existing usages" should {
        def withExistingUsedToggle(testCode: (ToggleUsageRepo, ToggleRepo, ToggleRegistration) => Any) = {
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockTogglesRepo = mock[ToggleRepo]
          doReturn(Seq.empty).when(mockToggleUsagesRepo).getByUser(anyString())

          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          // given
          val toggleDefinition = ToggleDefinition(toggle_name = "EXISTING_USED_TOGGLE", description = "EXISTING_USED_TOGGLE DESCRIPTION", toggle_type = "Temporary")
          val toggleRegistration = ToggleRegistration(user = SAMPLE_USER_SERVICE, toggles = Seq(toggleDefinition))
          setupExistingToggle(mockTogglesRepo, toggleDefinition)
          setupUsedToggle(mockToggleUsagesRepo, toggleDefinition, "existing-using-service")

          // when
          toggleUsagesService.register(toggleRegistration)

          testCode(mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration)
        }

        "not add another toggle" in withExistingUsedToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          // then
          val toggleUpdate = ToggleUpdate(toggle_name = "EXISTING_USED_TOGGLE", toggle_state = Some(false), description = Some("EXISTING_USED_TOGGLE DESCRIPTION"), toggle_type = Some("Temporary"), modified_by = "deployment")
          verify(mockTogglesRepo, never()).create(any())

        }

        "add usage of that toggle" in withExistingUsedToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          // then
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).upsert(SAMPLE_USER_SERVICE, "EXISTING_USED_TOGGLE")
        }
      }

      "there was another toggle used by the same service" should {
        val OBSOLETE_TOGGLE: String = "OBSOLETE_TOGGLE"

        "delete the other toggle's usage" in {
          // given
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockToggleRepo = mock[ToggleRepo]

          when(mockToggleRepo.get(TOGGLE_IN_USE)).thenReturn(None)
          when(mockToggleUsagesRepo.getByUser(SAMPLE_USER_SERVICE)).thenReturn(Seq(ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE)))
          when(mockToggleRepo.create(any())).thenReturn(Future(true))

          // when
          val toggleUsageService = createToggleUsageService(mockToggleRepo, mockToggleUsagesRepo)
          toggleUsageService.register(ToggleRegistration(SAMPLE_USER_SERVICE, Seq(ToggleDefinition(TOGGLE_IN_USE, "description", "Temporary"))))

          // then
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).upsert(SAMPLE_USER_SERVICE, TOGGLE_IN_USE)
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE)
        }
      }

      "there were other multiple toggles used by the same service" should {
        val OBSOLETE_TOGGLE_1: String = "OBSOLETE_TOGGLE_1"
        val OBSOLETE_TOGGLE_2: String = "OBSOLETE_TOGGLE_2"
        val OBSOLETE_TOGGLE_3: String = "OBSOLETE_TOGGLE_3"

        "delete the other toggles usages" in {
          // given
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockToggleRepo = mock[ToggleRepo]

          when(mockToggleRepo.get(TOGGLE_IN_USE)).thenReturn(None)
          when(mockToggleUsagesRepo.getByUser(SAMPLE_USER_SERVICE)).thenReturn(Seq(
            ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE_1),
            ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE_2),
            ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE_3)
          ))
          when(mockToggleRepo.create(any())).thenReturn(Future(true))

          // when
          val toggleUsageService = createToggleUsageService(mockToggleRepo, mockToggleUsagesRepo)
          toggleUsageService.register(ToggleRegistration(SAMPLE_USER_SERVICE, Seq(ToggleDefinition(TOGGLE_IN_USE, "description", "Temporary"))))

          // then
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).upsert(SAMPLE_USER_SERVICE, TOGGLE_IN_USE)
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE_1)
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE_2)
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE_3)
        }
      }

      "toggle creations fails" should {

        def withBrandNewToggle(testCode: (ToggleUsageRepo, ToggleRepo, ToggleRegistration) => Any) = {
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockTogglesRepo = mock[ToggleRepo]
          doReturn(Seq.empty).when(mockToggleUsagesRepo).getByUser(anyString())

          // given
          val toggleDefinition = ToggleDefinition(toggle_name = "BRAND_NEW_TOGGLE", description = "BRAND_NEW_TOGGLE DESCRIPTION", toggle_type = "Temporary")
          val toggleRegistration = ToggleRegistration(user = SAMPLE_USER_SERVICE, toggles = Seq(toggleDefinition))
          setupNonexistentToggle(mockTogglesRepo, toggleDefinition)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition)

          when(mockTogglesRepo.create(any())).thenReturn(Future(false))

          // when
          testCode(mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration)

        }

        "not register usage" in withBrandNewToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          toggleUsagesService.register(toggleRegistration)

          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000).never()).upsert(SAMPLE_USER_SERVICE, TOGGLE_IN_USE)
        }

        "throw an exception" in withBrandNewToggle { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          toggleUsagesService.register(toggleRegistration).map {
            result: Boolean => assert(!result)
          }
        }
      }
    }

    "called with multiple (3) existing toggles" when {

      "all the 3 toggles were not used before" should {

        def withMultipleExistingUnusedToggles(testCode: (ToggleUsageRepo, ToggleRepo, ToggleRegistration) => Any) = {
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockTogglesRepo = mock[ToggleRepo]
          doReturn(Seq.empty).when(mockToggleUsagesRepo).getByUser(anyString())

          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          // given

          val toggleDefinition_1 = ToggleDefinition(toggle_name = "EXISTING_TOGGLE_1", description = "EXISTING_TOGGLE_1 DESCRIPTION", toggle_type = "Temporary")
          setupExistingToggle(mockTogglesRepo, toggleDefinition_1)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition_1)

          val toggleDefinition_2 = ToggleDefinition(toggle_name = "EXISTING_TOGGLE_2", description = "EXISTING_TOGGLE_2 DESCRIPTION", toggle_type = "Temporary")
          setupExistingToggle(mockTogglesRepo, toggleDefinition_2)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition_2)

          val toggleDefinition_3 = ToggleDefinition(toggle_name = "EXISTING_TOGGLE_3", description = "EXISTING_TOGGLE_3 DESCRIPTION", toggle_type = "Temporary")
          setupExistingToggle(mockTogglesRepo, toggleDefinition_3)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition_3)

          val toggleRegistration = ToggleRegistration(user = SAMPLE_USER_SERVICE, toggles = Seq(toggleDefinition_1, toggleDefinition_2, toggleDefinition_3))

          // when
          toggleUsagesService.register(toggleRegistration)
          testCode(mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration)
        }

        "record usages of all 3 toggles" in withMultipleExistingUnusedToggles { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val existingToggleUsage_1 = ToggleUsage(toggle_name = "EXISTING_TOGGLE_1", user = SAMPLE_USER_SERVICE)
          val existingToggleUsage_2 = ToggleUsage(toggle_name = "EXISTING_TOGGLE_2", user = SAMPLE_USER_SERVICE)
          val existingToggleUsage_3 = ToggleUsage(toggle_name = "EXISTING_TOGGLE_3", user = SAMPLE_USER_SERVICE)

          val argumentCaptor: ArgumentCaptor[ToggleUsage] = ArgumentCaptor.forClass(classOf[ToggleUsage])
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000).times(3)).upsert(anyString(), anyString())

          val toggleUsages: Seq[ToggleUsage] = argumentCaptor.getAllValues().asScala
          toggleUsages.contains(existingToggleUsage_1)
          toggleUsages.contains(existingToggleUsage_2)
          toggleUsages.contains(existingToggleUsage_3)
        }

      }

      "there were other different toggles used before" should {
        val OBSOLETE_TOGGLE_1: String = "OBSOLETE_TOGGLE_1"
        val OBSOLETE_TOGGLE_2: String = "OBSOLETE_TOGGLE_2"
        val OBSOLETE_TOGGLE_3: String = "OBSOLETE_TOGGLE_3"

        def withMultipleExistingUnusedToggles(testCode: (ToggleUsageRepo, ToggleRepo, ToggleRegistration) => Any) = {
          val mockToggleUsagesRepo = mock[ToggleUsageRepo]
          val mockTogglesRepo = mock[ToggleRepo]

          val toggleUsagesService = createToggleUsageService(mockTogglesRepo, mockToggleUsagesRepo)

          // given
          val toggleDefinition_1 = ToggleDefinition(toggle_name = "EXISTING_TOGGLE_1", description = "EXISTING_TOGGLE_1 DESCRIPTION", toggle_type = "Temporary")
          setupExistingToggle(mockTogglesRepo, toggleDefinition_1)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition_1)

          val toggleDefinition_2 = ToggleDefinition(toggle_name = "EXISTING_TOGGLE_2", description = "EXISTING_TOGGLE_2 DESCRIPTION", toggle_type = "Temporary")
          setupExistingToggle(mockTogglesRepo, toggleDefinition_2)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition_2)

          val toggleDefinition_3 = ToggleDefinition(toggle_name = "EXISTING_TOGGLE_3", description = "EXISTING_TOGGLE_3 DESCRIPTION", toggle_type = "Temporary")
          setupExistingToggle(mockTogglesRepo, toggleDefinition_3)
          setupUnusedToggle(mockToggleUsagesRepo, toggleDefinition_3)

          val toggleRegistration = ToggleRegistration(user = SAMPLE_USER_SERVICE, toggles = Seq(toggleDefinition_1, toggleDefinition_2, toggleDefinition_3))

          when(mockToggleUsagesRepo.getByUser(SAMPLE_USER_SERVICE)).thenReturn(Seq(
            ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE_1),
            ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE_2),
            ToggleUsage(user = SAMPLE_USER_SERVICE, toggle_name = OBSOLETE_TOGGLE_3)
          ))
          when(mockTogglesRepo.create(any())).thenReturn(Future(true))

          // when
          toggleUsagesService.register(toggleRegistration)
          testCode(mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration)
        }

        "record usages of all 3 toggles" in withMultipleExistingUnusedToggles { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>
          val existingToggleUsage_1 = ToggleUsage(toggle_name = "EXISTING_TOGGLE_1", user = SAMPLE_USER_SERVICE)
          val existingToggleUsage_2 = ToggleUsage(toggle_name = "EXISTING_TOGGLE_2", user = SAMPLE_USER_SERVICE)
          val existingToggleUsage_3 = ToggleUsage(toggle_name = "EXISTING_TOGGLE_3", user = SAMPLE_USER_SERVICE)

          val argumentCaptor: ArgumentCaptor[ToggleUsage] = ArgumentCaptor.forClass(classOf[ToggleUsage])
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000).times(3)).upsert(anyString(), anyString())

          val toggleUsages: Seq[ToggleUsage] = argumentCaptor.getAllValues().asScala
          toggleUsages.contains(existingToggleUsage_1)
          toggleUsages.contains(existingToggleUsage_2)
          toggleUsages.contains(existingToggleUsage_3)
        }

        "delete the other different toggles usages" in withMultipleExistingUnusedToggles { (mockToggleUsagesRepo, mockTogglesRepo, toggleRegistration) =>

          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE_1)
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE_2)
          verify(mockToggleUsagesRepo, org.mockito.Mockito.timeout(1000)).delete(SAMPLE_USER_SERVICE, OBSOLETE_TOGGLE_3)
        }

      }
    }

    // helper methods

    def setupNonexistentToggle(mockTogglesRepo: ToggleRepo, toggleDefinition: ToggleDefinition) = {
      when(mockTogglesRepo.get(toggleDefinition.toggle_name)).thenReturn(None)
    }

    def setupExistingToggle(mockTogglesRepo: ToggleRepo, toggleDefinition: ToggleDefinition) = {
      val toggle = ToggleBuilder(toggle_name = toggleDefinition.toggle_name).build
      when(mockTogglesRepo.get(toggleDefinition.toggle_name)).thenReturn(Some(toggle))
    }

    def setupUnusedToggle(mockToggleUsagesRepo: ToggleUsageRepo, toggleDefinition: ToggleDefinition) = {
      when(mockToggleUsagesRepo.getByToggleName(toggleDefinition.toggle_name)).thenReturn(Seq.empty)
    }

    def setupUsedToggle(mockToggleUsagesRepo: ToggleUsageRepo, toggleDefinition: ToggleDefinition, existingUser: String = "existing-using-service") = {
      val existingToggleUsage = ToggleUsage(toggle_name = toggleDefinition.toggle_name, user = existingUser)
      when(mockToggleUsagesRepo.getByToggleName(toggleDefinition.toggle_name)).thenReturn(Seq(existingToggleUsage))
    }

  }

  ".findUnusedToggles" when {

    "there is no toggle usage" should {

      "return all toggles" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val unusedToggles = Seq(ToggleBuilder("IRRELEVANT_TOGGLE_1").build, ToggleBuilder("IRRELEVANT_TOGGLE_2").build)

        doReturn(Future { unusedToggles }).when(toggleRepo).getAll()
        doReturn(Seq.empty).when(toggleUsageRepo).getUsedTogglesNames()

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.findUnusedToggles()) { result =>
          result should equal(unusedToggles)
        }
      }
    }

    "there are usages for some toggle" should {

      "return all the other toggles" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val UNUSED_TOGGLE_2 = ToggleBuilder(toggle_name = "UNUSED_TOGGLE_2").build
        val UNUSED_TOGGLE_3 = ToggleBuilder(toggle_name = "UNUSED_TOGGLE_3").build

        val USED_TOGGLE = ToggleBuilder(toggle_name = "USED_TOGGLE").build

        val allToggles = Future {
          Seq(
            USED_TOGGLE,
            UNUSED_TOGGLE_2,
            UNUSED_TOGGLE_3
          )
        }

        val unusedToggles = Seq(UNUSED_TOGGLE_2, UNUSED_TOGGLE_3)

        doReturn(allToggles).when(toggleRepo).getAll()
        doReturn(Seq(USED_TOGGLE.toggle_name)).when(toggleUsageRepo).getUsedTogglesNames()

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.findUnusedToggles()) { result =>
          result should equal(unusedToggles)
        }
      }
    }

  }

  ".deleteUnusedToggles" when {

    "there is no toggle usage" should {

      "return all toggles" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val unusedToggles = Seq(ToggleBuilder(toggle_name = "UNUSED_TOGGLE_1").build, ToggleBuilder(toggle_name = "UNUSED_TOGGLE_2").build)
        doReturn(Future {
          unusedToggles
        }).when(toggleRepo).getAll()
        doReturn(Seq.empty).when(toggleUsageRepo).getUsedTogglesNames()

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.deleteUnusedToggles()) { result =>
          result should equal(unusedToggles)

          unusedToggles.foreach { t =>
            verify(toggleRepo).delete(t.toggle_name)
          }
        }
      }
    }

    "there are usages for some toggle" should {

      "return all unused toggles" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val UNUSED_TOGGLE_2 = ToggleBuilder(toggle_name = "UNUSED_TOGGLE_2").build
        val UNUSED_TOGGLE_3 = ToggleBuilder(toggle_name = "UNUSED_TOGGLE_3").build

        val USED_TOGGLE = ToggleBuilder(toggle_name = "USED_TOGGLE").build

        val allToggles = Future {
          Seq(
            USED_TOGGLE,
            UNUSED_TOGGLE_2,
            UNUSED_TOGGLE_3
          )
        }

        val unusedToggles = Seq(UNUSED_TOGGLE_2, UNUSED_TOGGLE_3)

        doReturn(allToggles).when(toggleRepo).getAll()
        doReturn(Seq(USED_TOGGLE.toggle_name)).when(toggleUsageRepo).getUsedTogglesNames()

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.deleteUnusedToggles()) { result =>
          result should equal(unusedToggles)

          verify(toggleRepo, never).delete(USED_TOGGLE.toggle_name)
          verify(toggleRepo).delete(UNUSED_TOGGLE_2.toggle_name)
          verify(toggleRepo).delete(UNUSED_TOGGLE_3.toggle_name)
        }

      }
    }

  }

  ".getCurrentCounts" when {

    "there are N toggles but there is no usage" should {

      "return 0 used_active, 0 used_expired, N unused" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val unusedToggles = Seq(ToggleBuilder("IRRELEVANT_TOGGLE_1").build, ToggleBuilder("IRRELEVANT_TOGGLE_2").build)

        when(toggleRepo.getAll()).thenReturn(Future { unusedToggles })
        when(toggleUsageRepo.getUsedTogglesNames()).thenReturn(Seq.empty)

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.getCurrentCounts()) { result =>
          result should equal(ToggleCounts(
            DateTime.now.withTimeAtStartOfDay,
            SAMPLE_BANNER,
            SAMPLE_ENV,
            used_active = 0,
            unused = unusedToggles.length,
            used_expired = 0
          ))
        }
      }
    }

    "there are N toggles and all of them are actively used" should {

      "return N used_active, 0 used_expired, 0 unused" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val USED_TOGGLE_1 = ToggleBuilder(toggle_name = "USED_TOGGLE_1").build
        val USED_TOGGLE_2 = ToggleBuilder(toggle_name = "USED_TOGGLE_2").build
        val USED_TOGGLE_3 = ToggleBuilder(toggle_name = "USED_TOGGLE_3").build

        val allToggles = Seq(USED_TOGGLE_1, USED_TOGGLE_2, USED_TOGGLE_3)

        when(toggleRepo.getAll()).thenReturn(Future { allToggles })
        when(toggleUsageRepo.getUsedTogglesNames()).thenReturn(allToggles.map(_.toggle_name))

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.getCurrentCounts()) { result =>
          result should equal(ToggleCounts(
            DateTime.now.withTimeAtStartOfDay,
            SAMPLE_BANNER,
            SAMPLE_ENV,
            used_active = allToggles.length,
            unused = 0,
            used_expired = 0
          ))
        }
      }
    }

    "there are N toggles and all of them are used but EXPIRED" should {

      "return 0 used_active, N used_expired, 0 unused" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val USED_TOGGLE_1 = ToggleBuilder(toggle_name = "USED_TOGGLE_1").expired.build
        val USED_TOGGLE_2 = ToggleBuilder(toggle_name = "USED_TOGGLE_2").expired.build
        val USED_TOGGLE_3 = ToggleBuilder(toggle_name = "USED_TOGGLE_3").expired.build

        val allExpiredToggles = Seq(USED_TOGGLE_1, USED_TOGGLE_2, USED_TOGGLE_3)

        when(toggleRepo.getAll()).thenReturn(Future { allExpiredToggles })
        when(toggleUsageRepo.getUsedTogglesNames()).thenReturn(allExpiredToggles.map(_.toggle_name))

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.getCurrentCounts()) { result =>
          result should equal(ToggleCounts(
            DateTime.now.withTimeAtStartOfDay,
            SAMPLE_BANNER,
            SAMPLE_ENV,
            unused = 0,
            used_active = 0,
            used_expired = allExpiredToggles.length
          ))
        }
      }
    }

    "there are 6 toggles and 3 of them are used, but 2 are expired" should {

      "return 1 used, 2 expired, 3 unused" in {
        val toggleRepo = mock[ToggleRepo]
        val toggleUsageRepo = mock[ToggleUsageRepo]

        val unexpiredToggles = (0 until 4).map(i => ToggleBuilder(toggle_name = s"TOGGLE_${i}").build)
        val expiredToggles = (0 until 2).map(i => ToggleBuilder(toggle_name = s"EXPIRED_TOGGLE_${i}").expired.build)

        val allToggles = expiredToggles ++ unexpiredToggles
        val usedToggles = expiredToggles ++ Seq(unexpiredToggles(0))

        when(toggleRepo.getAll()).thenReturn(Future { allToggles })
        when(toggleUsageRepo.getUsedTogglesNames()).thenReturn(usedToggles.map(_.toggle_name))

        val sut = createToggleUsageService(toggleRepo, toggleUsageRepo)

        whenReady(sut.getCurrentCounts()) { result =>
          result should equal(ToggleCounts(
            DateTime.now.withTimeAtStartOfDay,
            SAMPLE_BANNER,
            SAMPLE_ENV,
            used_active = 1,
            unused = 3,
            used_expired = 2
          ))
        }
      }
    }

  }

  private def createToggleUsageService(
    toggleRepo:       ToggleRepo,
    toggleUsageRepo:  ToggleUsageRepo,
    toggleCountsRepo: ToggleCountsRepo = mock[ToggleCountsRepo]
  ) = {

    new ToggleUsageService(toggleUsageRepo, toggleRepo, toggleCountsRepo,
      Configuration.from(Map(Constants.HBC_ENV -> SAMPLE_ENV, Constants.HBC_BANNER -> SAMPLE_BANNER)))
  }
}
