provider "aws" {
  region = "${var.aws_region}"
}

module "mongo_replica_set" {
  source = "../../../../aws-ref-arch/terraform/mongo"

  admin_username          = "admin"
  admin_password_key      = "${var.admin_password_key}"
  mongo_keyfile           = "./mongo-key"
  replica_set_name        = "toggle_service-${terraform.env}"
  subnet_id               = "${element(var.subnet_ids_app,0)}"
  security_group          = "${var.app_security_group}"
  key_name                = "${var.key_name}"
  instance_type           = "t2.micro"
  db_name                 = "${var.db_name}"
  db_username             = "${var.db_username}"
  db_password_key         = "${var.db_password_key}"
  volume_size             = "${var.volume_size}"
  parameter_key_namespace = "/toggle-service/${var.hbc_banner}/${var.hbc_env}"
  hbc_banner              = "${var.hbc_banner}"
  hbc_group               = "${var.hbc_group}"
  hbc_env                 = "${var.hbc_env}"
}

terraform {
  backend "s3" {
    bucket = "rogue-infra"
    key    = "terraform/concourse/mongo-rs"
    region = "us-east-1"
  }
}
