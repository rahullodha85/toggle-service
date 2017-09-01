provider "aws" {
  region = "${var.aws_region}"
}

resource "aws_cloudwatch_log_metric_filter" "error" {
  name           = "${var.app_name}-${var.component}-error"
  pattern        = "error"
  log_group_name = "${var.log_group}"

  metric_transformation {
    name      = "ErrorCount"
    namespace = "LogMetrics"
    value     = "1"
  }
}

resource "aws_cloudwatch_log_metric_filter" "fatal" {
  name           = "${var.app_name}-${var.component}-fatal"
  pattern        = "fatal"
  log_group_name = "${var.log_group}"

  metric_transformation {
    name      = "FatalCount"
    namespace = "LogMetrics"
    value     = "1"
  }
}

resource "aws_cloudwatch_metric_alarm" "error" {
  alarm_name          = "${var.app_name}-${var.component}-error-alarm"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = "1"
  metric_name         = "ErrorCount"
  namespace           = "LogMetrics"
  period              = "60"
  statistic           = "Average"
  threshold           = "1"
  alarm_description   = "This metric monitors error message"

  alarm_actions       = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]
}

resource "aws_cloudwatch_metric_alarm" "fatal" {
  alarm_name          = "${var.app_name}-${var.component}-fatal-alarm"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = "1"
  metric_name         = "FatalCount"
  namespace           = "LogMetrics"
  period              = "60"
  statistic           = "Average"
  threshold           = "1"
  alarm_description   = "This metric monitors fatal message"

  alarm_actions       = [
    "${data.aws_sns_topic.pager_duty.arn}"
  ]
}

data "aws_sns_topic" "pager_duty" {
  name = "pager_duty"
}