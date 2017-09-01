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

variable "lb_name" {
  type        = "string"
  description = "Name of load balancer to create an alarm for"
}

variable "asg_min" {
  type        = "string"
  description = "Minimum instances for app autoscaling group (ex: \"1\")"
}