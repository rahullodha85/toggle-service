output "toggle-service-component" {
  value = "${module.toggle-service.component}"
}

output "elb_name" {
  value = "${module.toggle-service.elb_name}"
}

output "auto_scale_group_name" {
  value = "${module.toggle-service.asg_name}"
}

output "route_53_endpoint_fqdn" {
  value = "${module.toggle-service.fqdn}"
}

output "docker_image_deployed" {
  value = "${module.toggle-service.docker_image_deployed}"
}