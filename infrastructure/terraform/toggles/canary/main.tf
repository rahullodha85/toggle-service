
module "toggle-service" {
  source = "../../../../../aws-ref-arch/terraform/microservice"

  subnet_ids_app         = "${var.subnet_ids_app}"
  subnet_ids_elb         = "${var.subnet_ids_elb}"

  lb_security_group  = "${var.lb_security_group}"
  app_security_group = "${var.app_security_group}"
  key_name           = "${var.key_name}"
  app_port           = "${var.app_port}"
  asg_max            = "${var.asg_max}"
  asg_min            = "${var.asg_min}"
  asg_desired        = "${var.asg_desired}"
  app_name           = "${var.app_name}"
  ping_path          = "${var.ping_path}"
  instance_type      = "${var.instance_type}"
  shared_kms_key     = "${var.shared_kms_key}"
  hbc_banner         = "${var.hbc_banner}"
  hbc_group          = "${var.hbc_group}"
  hbc_env            = "${var.hbc_env}"
  component          = "${var.component}"
  image              = "${var.image}"
  additional_elb_to_associate_asg   = "${data.terraform_remote_state.prod_stack.elb_name}"


  env_vars           = {
    APP_NAME                  = "${var.app_name}"
    HBC_BANNER                = "${var.hbc_banner}"
    HBC_ENV                   = "${var.hbc_env}"
    MONGO_USER                = "${data.terraform_remote_state.mongo.db_username}"
    MONGO_PASSWORD_SECRET_KEY = "${data.terraform_remote_state.mongo.db_password_key}"
    MONGO_HOST                = "${data.terraform_remote_state.mongo.ip}/${data.terraform_remote_state.mongo.db_name}"
    MONGO_DB_NAME             = "${data.terraform_remote_state.mongo.db_name}"
    NEW_RELIC_LICENSE_KEY     = "${var.NEW_RELIC_LICENSE_KEY}"
    NEW_RELIC_APP_NAME        = "${var.NEW_RELIC_APP_NAME}"
  }


}

data "terraform_remote_state" "mongo" {
  backend = "s3"
  environment = "${var.PROD_TF_ENV}"

  config {
    bucket = "rogue-infra"
    key    = "terraform/concourse/mongo-rs"
    region = "us-east-1"
  }
}


module "cpu_alarm" {
  source    = "../cloudwatch/cpu-alarm"

  app_name  = "${var.app_name}"
  asg_name  = "${module.toggle-service.asg_name}"
  component = "${module.toggle-service.component}"
}

module "health_alarm" {
  source    = "../cloudwatch/health-alarm"

  component = "${module.toggle-service.component}"
  asg_min   = "${var.asg_min}"
  lb_name   = "${module.toggle-service.elb_name}"
  app_name  = "${module.toggle-service.app_name}"
}

module "logging_alarm" {
  source    = "../cloudwatch/logging-alarm"

  app_name  = "${module.toggle-service.app_name}"
  component = "${module.toggle-service.component}"
  log_group = "${module.toggle-service.log_group}"
}

terraform {
  backend "s3" {
    bucket = "rogue-infra"
    key    = "terraform/concourse/canary-toggle-stack"
    region = "us-east-1"
  }
}

data "terraform_remote_state" prod_stack {
  backend = "s3"
  config  {
    bucket = "rogue-infra"
    key    = "terraform/concourse/toggle-stack"
    region = "us-east-1"
  }

  environment = "${var.PROD_TF_ENV}"
}
