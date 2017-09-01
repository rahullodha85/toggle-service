module "mongo_ebs" {
  source = "../../../../../aws-ref-arch/terraform/mongo/ebs"
  replica_set_name        = "toggle_service-${terraform.env}"
  subnet_id               = "${element(var.subnet_ids_app,0)}"
  security_group          = "${var.app_security_group}"
  hbc_banner              = "${var.hbc_banner}"
  hbc_group               = "${var.hbc_group}"
  hbc_env                 = "${var.hbc_env}"
  volume_size             = "${var.volume_size}"
}



terraform {
  backend "s3" {
    bucket = "rogue-infra"
    key    = "terraform/concourse/ebs/mongo-ebs"
    region = "us-east-1"
  }
}
