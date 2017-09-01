output "ip" {
  value = "${module.mongo_replica_set.ip}"
}

output "db_name" {
  value = "${var.db_name}"
}

output "db_username" {
  value = "${var.db_username}"
}

output "db_password_key" {
  value = "${var.db_password_key}"
}
