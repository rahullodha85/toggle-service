package models

case class ToggleUpdateBuilder(
    toggle_name:  String          = "",
    toggle_state: Option[Boolean] = Some(false),
    description:  Option[String]  = None,
    toggle_type:  Option[String]  = None,
    modified_by:  String          = ""
) {

  def fromNew(toggleDefinition: ToggleDefinition): ToggleUpdateBuilder = {
    ToggleUpdateBuilder(
      toggle_name = toggleDefinition.toggle_name,
      toggle_state = Some(false),
      description = Some(toggleDefinition.description),
      toggle_type = Some(toggleDefinition.toggle_type),
      modified_by = "deployment"
    )
  }

  def build(): ToggleUpdate = {
    ToggleUpdate(
      toggle_name,
      toggle_state,
      description,
      toggle_type,
      modified_by
    )
  }

}
