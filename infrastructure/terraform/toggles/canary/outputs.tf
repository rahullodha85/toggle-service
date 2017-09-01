output "toggle-service-component" {
  value = "${module.toggle-service.component}"
}

output "canary_elb_name" {
  value = "${module.toggle-service.elb_name}"
}

output "canary_auto_scale_group_name" {
  value = "${module.toggle-service.asg_name}"
}

output "canary_route_53_endpoint_fqdn" {
  value = "${module.toggle-service.fqdn}"
}

output "canary_docker_image_deployed" {
  value = "${module.toggle-service.docker_image_deployed}"
}

output "production_elb_name" {
  value = "${data.terraform_remote_state.prod_stack.elb_name}"
}

output "production_route_53_endpoint_fqdn" {
  value = "${data.terraform_remote_state.prod_stack.fqdn}"
}