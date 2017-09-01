provider "aws" {
  region = "${var.aws_region}"
}

resource "aws_cloudwatch_metric_alarm" "elb_health_check" {
  alarm_name          = "${var.app_name}-${var.component}-health-check"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = "1"
  metric_name         = "UnHealthyHostCount"
  namespace           = "AWS/ELB"
  period              = "300"
  statistic           = "Average"
  threshold           = "1"
  alarm_description   = "This metric monitors if an ELB instance has been unhealthy for 5 minutes"

  alarm_actions       = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]

  ok_actions          = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]

  dimensions {
    LoadBalancerName = "${var.lb_name}"
  }
}

resource "aws_cloudwatch_metric_alarm" "elb_healthy_instances" {
  alarm_name          = "${var.app_name}-${var.component}-healthy-instances"
  comparison_operator = "LessThanThreshold"
  evaluation_periods  = "1"
  metric_name         = "HealthyHostCount"
  namespace           = "AWS/ELB"
  period              = "60"
  statistic           = "Minimum"
  threshold           = "${var.asg_min}"
  alarm_description   = "This metric monitors the number of healthy instances in an ELB"

  alarm_actions       = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]

  ok_actions          = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]

  dimensions {
    LoadBalancerName = "${var.lb_name}"
  }
}

data "aws_sns_topic" "pager_duty" {
  name = "pager_duty"
}