package models

import constants.Constants
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import org.joda.time.DateTime

// for auth/admin panel purposes
case class HbcUser(username: String, password: String, access: String)

// I gave up/was too lazy to figure out joda-time for our swagger
// just report datetime as string since its json anyway...
// no need to actually use this class
// Feel free to add joda time to swagger  :)
case class ToggleForSwagger(
  toggle_name:        String,
  toggle_state:       Boolean,
  created_timestamp:  String,
  modified_timestamp: String,
  description:        Option[String] = None,
  toggle_type:        String         = "",
  modified_by:        String         = ""
)

case class Toggle(
  toggle_name:        String,
  toggle_state:       Boolean,
  created_timestamp:  DateTime       = new DateTime(),
  modified_timestamp: DateTime       = new DateTime(),
  description:        Option[String] = None,
  toggle_type:        String         = "",
  modified_by:        String         = ""
)

// create/update 
case class ToggleUpdate(
  toggle_name:  String,
  toggle_state: Option[Boolean] = None,
  description:  Option[String]  = None,
  toggle_type:  Option[String]  = None,
  modified_by:  String
)

case class ToggleDefinition(
  toggle_name: String,
  description: String,
  toggle_type: String
)

case class ToggleRegistration(
  user:    String,
  toggles: Seq[ToggleDefinition]
)

case class ToggleUsage(
  toggle_name: String,
  user:        String
)

case class ToggleCounts(
  date:         DateTime,
  banner:       String   = "",
  env:          String   = "",
  used_active:  Int,
  used_expired: Int,
  unused:       Int
)

trait ToggleSerialization {

  implicit val jodaToggleDateTimeReads = Reads.jodaDateReads(Constants.ZULU_DATE_FORMAT)
  implicit val jodaToggleDateTimeWrites = Writes.jodaDateWrites(Constants.ZULU_DATE_FORMAT)

  implicit val readToggleModel: Reads[Toggle] = (
    (__ \ Constants.TOGGLE_NAME).read[String](pattern(Constants.TOGGLE_NAME_REGEX)) and
    (__ \ Constants.TOGGLE_STATE).read[Boolean] and
    (__ \ Constants.CREATED_TIMESTAMP).read[DateTime](jodaToggleDateTimeReads) and
    (__ \ Constants.MODIFIED_TIMESTAMP).read[DateTime](jodaToggleDateTimeReads) and
    (__ \ Constants.DESCRIPTION).readNullable[String] and
    (__ \ Constants.TOGGLE_TYPE).read[String] and
    (__ \ Constants.MODIFIED_BY).read[String]
  )(Toggle)

  implicit val writeToggleModel: Writes[Toggle] = (
    (__ \ Constants.TOGGLE_NAME).write[String] and
    (__ \ Constants.TOGGLE_STATE).write[Boolean] and
    (__ \ Constants.CREATED_TIMESTAMP).write[DateTime](jodaToggleDateTimeWrites) and
    (__ \ Constants.MODIFIED_TIMESTAMP).write[DateTime](jodaToggleDateTimeWrites) and
    (__ \ Constants.DESCRIPTION).writeNullable[String] and
    (__ \ Constants.TOGGLE_TYPE).write[String] and
    (__ \ Constants.MODIFIED_BY).write[String]
  )(unlift(Toggle.unapply))

  implicit val readToggleUpdateModel: Reads[ToggleUpdate] = (
    (__ \ Constants.TOGGLE_NAME).read[String](pattern(Constants.TOGGLE_NAME_REGEX)) and
    (__ \ Constants.TOGGLE_STATE).readNullable[Boolean] and
    (__ \ Constants.DESCRIPTION).readNullable[String] and
    (__ \ Constants.TOGGLE_TYPE).readNullable[String] and
    (__ \ Constants.MODIFIED_BY).read[String]
  )(ToggleUpdate)

  implicit val writeToggleUpdateModel: Writes[ToggleUpdate] = (
    (__ \ Constants.TOGGLE_NAME).write[String] and
    (__ \ Constants.TOGGLE_STATE).writeNullable[Boolean] and
    (__ \ Constants.DESCRIPTION).writeNullable[String] and
    (__ \ Constants.TOGGLE_TYPE).writeNullable[String] and
    (__ \ Constants.MODIFIED_BY).write[String]
  )(unlift(ToggleUpdate.unapply))

  implicit val readToggleDefinitionModel: Reads[ToggleDefinition] = (
    (__ \ Constants.TOGGLE_NAME).read[String](pattern(Constants.TOGGLE_NAME_REGEX)) and
    (__ \ Constants.DESCRIPTION).read[String] and
    (__ \ Constants.TOGGLE_TYPE).read[String]
  )(ToggleDefinition)

  implicit val writeToggleDefinitionModel: Writes[ToggleDefinition] = (
    (__ \ Constants.TOGGLE_NAME).write[String] and
    (__ \ Constants.DESCRIPTION).write[String] and
    (__ \ Constants.TOGGLE_TYPE).write[String]
  )(unlift(ToggleDefinition.unapply))

  implicit val readToggleRegistrationModel: Reads[ToggleRegistration] = (
    (__ \ Constants.USER).read[String] and
    (__ \ Constants.TOGGLES).read[Seq[ToggleDefinition]]
  )(ToggleRegistration)

  implicit val writeToggleRegistrationModel: Writes[ToggleRegistration] = (
    (__ \ Constants.USER).write[String] and
    (__ \ Constants.TOGGLES).write[Seq[ToggleDefinition]]
  )(unlift(ToggleRegistration.unapply))

  implicit val readToggleUsageModel: Reads[ToggleUsage] = (
    (__ \ Constants.TOGGLE_NAME).read[String] and
    (__ \ Constants.USER).read[String]
  )(ToggleUsage)

  implicit val writeToggleUsageModel: Writes[ToggleUsage] = (
    (__ \ Constants.TOGGLE_NAME).write[String] and
    (__ \ Constants.USER).write[String]
  )(unlift(ToggleUsage.unapply))

  implicit val yyyymmddDateTimeReads = Reads.jodaDateReads("yyyyMMdd")
  implicit val yyyymmdddateTimeWrites = Writes.jodaDateWrites("yyyyMMdd")

  implicit val readToggleCountsModel: Reads[ToggleCounts] = (
    (__ \ Fields.DATE).read[DateTime](yyyymmddDateTimeReads) and
    (__ \ Fields.BANNER).read[String] and
    (__ \ Fields.ENV).read[String] and
    (__ \ Fields.USED_ACTIVE).read[Int] and
    (__ \ Fields.USED_EXPIRED).read[Int] and
    (__ \ Fields.UNUSED).read[Int]
  )(ToggleCounts)

  implicit val writeToggleCountsModel: Writes[ToggleCounts] = (
    (__ \ Fields.DATE).write[DateTime](yyyymmdddateTimeWrites) and
    (__ \ Fields.BANNER).write[String] and
    (__ \ Fields.ENV).write[String] and
    (__ \ Fields.USED_ACTIVE).write[Int] and
    (__ \ Fields.USED_EXPIRED).write[Int] and
    (__ \ Fields.UNUSED).write[Int]
  )(unlift(ToggleCounts.unapply))

}

object ToggleSerialization extends ToggleSerialization

// *******************************
//  TOGGLE HISTORY
// *******************************

// see above, just a case class to appease the swagger god, 
// no need to use it
case class ToggleHistoryForSwagger(
  toggle_name:        String,
  toggle_state:       Boolean,
  modified_timestamp: String,
  description:        Option[String],
  toggle_type:        Option[String],
  modified_by:        String,
  action:             String
)

case class ToggleHistory(
  toggle_name:        String,
  toggle_state:       Boolean,
  modified_timestamp: DateTime,
  description:        Option[String],
  toggle_type:        Option[String],
  modified_by:        String,
  action:             String
)

trait ToggleHistorySerialization {

  implicit val jodaToggleHistoryDateTimeReads = Reads.jodaDateReads(Constants.ZULU_DATE_FORMAT)
  implicit val jodaToggleHistoryDateTimeWrites = Writes.jodaDateWrites(Constants.ZULU_DATE_FORMAT)

  implicit val readHistoryJsonObject: Reads[ToggleHistory] = (
    (__ \ Constants.TOGGLE_NAME).read[String](pattern(Constants.TOGGLE_NAME_REGEX)) and
    (__ \ Constants.TOGGLE_STATE).read[Boolean] and
    (__ \ Constants.MODIFIED_TIMESTAMP).read[DateTime](jodaToggleHistoryDateTimeReads) and
    (__ \ Constants.DESCRIPTION).readNullable[String] and
    (__ \ Constants.TOGGLE_TYPE).readNullable[String] and
    (__ \ Constants.MODIFIED_BY).read[String] and
    (__ \ Constants.ACTION).read[String]
  )(ToggleHistory)

  implicit val writeHistoryJsonObject: Writes[ToggleHistory] = (
    (__ \ Constants.TOGGLE_NAME).write[String] and
    (__ \ Constants.TOGGLE_STATE).write[Boolean] and
    (__ \ Constants.MODIFIED_TIMESTAMP).write[DateTime](jodaToggleHistoryDateTimeWrites) and
    (__ \ Constants.DESCRIPTION).writeNullable[String] and
    (__ \ Constants.TOGGLE_TYPE).writeNullable[String] and
    (__ \ Constants.MODIFIED_BY).write[String] and
    (__ \ Constants.ACTION).write[String]
  )(unlift(ToggleHistory.unapply))
}

object ToggleHistorySerialization extends ToggleHistorySerialization
