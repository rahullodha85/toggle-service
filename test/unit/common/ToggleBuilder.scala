package unit.common

import models.Toggle
import org.joda.time.DateTime
import constants.Constants.EXPIRATION_WINDOW_DAYS

case class ToggleBuilder(
    toggle_name:        String         = "",
    toggle_state:       Boolean        = false,
    created_timestamp:  DateTime       = new DateTime(),
    modified_timestamp: DateTime       = new DateTime(),
    description:        Option[String] = None,
    toggle_type:        String         = "",
    modified_by:        String         = ""
) {

  def build: Toggle = {
    Toggle(toggle_name, toggle_state, created_timestamp, modified_timestamp, description, toggle_type, modified_by)
  }

  def expired: ToggleBuilder = {
    this.copy(modified_timestamp = DateTime.now().minusDays(EXPIRATION_WINDOW_DAYS))
  }

}
