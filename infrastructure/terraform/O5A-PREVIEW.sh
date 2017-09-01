#!/usr/bin/env bash

export TF_VAR_subnet_ids_app='["subnet-809e0aac", "subnet-7beca033"]'
export TF_VAR_subnet_ids_elb='["subnet-e88612c4", "subnet-ced39f86"]'
export TF_VAR_lb_security_group=sg-c70f37b6
export TF_VAR_app_security_group=sg-400f3731
export TF_VAR_key_name=rogue_squadron_shared
export TF_VAR_app_port=9860
export TF_VAR_asg_max=3
export TF_VAR_asg_min=1
export TF_VAR_asg_desired=1
export TF_VAR_ping_path=/v1/toggle-service
export TF_VAR_instance_type=t2.micro
export TF_VAR_shared_kms_key=57b7e9e3-50a7-4f11-9731-b4b399e9558e
export TF_VAR_hbc_banner=o5a
export TF_VAR_hbc_group=rogue-squadron
export TF_VAR_hbc_env=preview
