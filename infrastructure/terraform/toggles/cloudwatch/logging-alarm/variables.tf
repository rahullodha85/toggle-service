variable "aws_region" {
  default = "us-east-1"
}

variable "app_name" {
  type        = "string"
  description = "Name of application (ex: \"hello-world\")"
}

variable "component" {
  type        = "string"
  description = "Name of component (ex: \"live\")"
}

variable "log_group" {
  type        = "string"
  description = "Name of the application log group"
}