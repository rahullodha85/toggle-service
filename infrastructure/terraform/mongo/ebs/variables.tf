variable "aws_region" {
  type        = "string"
  description = "AWS region to launch servers."
  default     = "us-east-1"
}


variable "volume_size" {
  type = "string"
  description = "EBS Volume Size (data) for mongo node"
  default = "200"
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

variable "app_security_group" {
  type        = "string"
  default = ""
}

variable "subnet_ids_app" {
  type = "list"
}