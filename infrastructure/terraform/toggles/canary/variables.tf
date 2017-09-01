
variable "NEW_RELIC_LICENSE_KEY" {
  type        = "string"
  description = "New relic license key"
}

variable "NEW_RELIC_APP_NAME" {
  type        = "string"
  description = "New relic app name"
}

variable "app_name" {
  default = "toggle-service"
}

variable "asg_min" {
  default = "1"
}

variable "lb_security_group" {
  type        = "string"
  default = ""
}
variable "app_security_group" {
  type        = "string"
  default = ""
}
variable "key_name" {
  type        = "string"
  default = ""
}
variable "app_port" {
  type        = "string"
  default = ""
}
variable "asg_max" {
  type        = "string"
  default = ""
}
variable "asg_desired" {
  type        = "string"
  default = ""
}
variable "instance_type" {
  type        = "string"
  default = ""
}
variable "shared_kms_key" {
  type        = "string"
  default = ""
}
variable "hbc_banner" {
  type        = "string"
  default = ""
}
variable "hbc_group" {
  type        = "string"
  default = ""
}
variable "hbc_env" {
  type        = "string"
  default = ""
}

variable "ping_path" {
  default = ""
}

variable "subnet_ids_app" {
  type = "list"
}

variable "subnet_ids_elb" {
  type = "list"
}

#
#
# Module Specific
#
#

variable "component" {
  type        = "string"
  description = "Name of component (ex: \"live\")"
}

variable "image" {
  type        = "string"
  description = "Docker image for microservice, including version (ex: \"hbc-docker.jfrog.io/toggle-service:latest\")"
}

variable "PROD_TF_ENV" {
  type        = "string"
  description = "environment containing the live production stack"
  default     = ""
}