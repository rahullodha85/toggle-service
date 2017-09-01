provider "aws" {
  region = "${var.aws_region}"
}

resource "aws_autoscaling_policy" "scale_up" {
  name                   = "${var.app_name}-${terraform.env}-${var.component}-scale-up-policy"
  scaling_adjustment     = 1
  adjustment_type        = "ChangeInCapacity"
  cooldown               = 120
  autoscaling_group_name = "${var.asg_name}"
}

resource "aws_autoscaling_policy" "scale_down" {
  name                   = "${var.app_name}-${terraform.env}-${var.component}-scale_down-policy"
  scaling_adjustment     = -1
  adjustment_type        = "ChangeInCapacity"
  cooldown               = 120
  autoscaling_group_name = "${var.asg_name}"
}

resource "aws_cloudwatch_metric_alarm" "max_cpu_utilization" {
  alarm_name          = "${var.app_name}-${terraform.env}-${var.component}-cpu-above-75-pct"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = "1"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = "120"
  statistic           = "Average"
  threshold           = "75"
  alarm_description   = "This metric monitors ec2 max cpu utilization"

  alarm_actions       = [
    "${aws_autoscaling_policy.scale_up.arn}",
    "${data.aws_sns_topic.pager_duty.arn}"
  ]

  ok_actions          = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]

  dimensions {
    AutoScalingGroupName = "${var.asg_name}"
  }

}

resource "aws_cloudwatch_metric_alarm" "min_cpu_utilization" {
  alarm_name          = "${var.app_name}-${terraform.env}-${var.component}-cpu-below-5-pct"
  comparison_operator = "LessThanOrEqualToThreshold"
  evaluation_periods  = "1"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = "300"
  statistic           = "Average"
  threshold           = "5"

  dimensions {
    AutoScalingGroupName = "${var.asg_name}"
  }

  alarm_description   = "This metric monitors ec2 min cpu utilization"

  alarm_actions       = [
    "${aws_autoscaling_policy.scale_down.arn}"
  ]
}

data "aws_sns_topic" "pager_duty" {
  name = "pager_duty"
}