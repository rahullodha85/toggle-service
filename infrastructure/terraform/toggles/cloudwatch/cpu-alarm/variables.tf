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

variable "asg_name" {
  type        = "string"
  description = "Name of asg to create an alarm for (ex: \"monitoring-prometheus\")"
}