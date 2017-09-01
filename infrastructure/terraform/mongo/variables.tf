variable "aws_region" {
  type        = "string"
  description = "AWS region to launch servers."
  default     = "us-east-1"
}

variable "admin_password_key" {
  type        = "string"
  description = "Admin DB's password parameter store key"
  default     = "mongoAdminPassword"
}

variable "db_name" {
  type        = "string"
  description = "Name for DB"
  default     = "toggle"
}

variable "db_username" {
  type        = "string"
  description = "Username for DB"
  default     = "username"
}

variable "db_password_key" {
  type        = "string"
  description = "DB password parameter store key"
  default     = "mongoPassword"
}

variable "volume_size" {
  description = "EBS Volume Size (data) for node"
  default     = "200"
}


variable "shared_kms_key" {
  type        = "string"
  default     = ""
}
variable "hbc_banner" {
  type        = "string"
  default     = ""
}
variable "hbc_group" {
  type        = "string"
  default     = ""
}
variable "hbc_env" {
  type        = "string"
  default     = ""
}

variable "key_name" {
  type        = "string"
  default     = ""
}

variable "app_security_group" {
  type        = "string"
  default     = ""
}

variable "subnet_ids_app" {
  type = "list"
}