#!/usr/bin/env bats
setup() {
  export TEST=true
  unset $(env | grep GO_DEPENDENCY_LOCATOR_ | cut -d'=' -f1)
}

teardown() {
  echo "exit status: ${status}"
  echo $output

  unset GO_DEPENDENCY_LOCATOR_TEST
  unset DOCKER_INSTANCE
  unset TEST

  rm -f toggles.json saks-alfresco-toggles.json website-backend-toggles.json output.log toggle-registration-response.out
}

@test "should fail when GO_AUTH is not set" {
  unset GO_AUTH

  run ../toggle-registration/register.sh --env prod --appname fake-service --errorcode 42
  [ "$status" -eq 42 ]
}

@test "should fail with code 42 when --env is invalid and --errorcode is 42" {
  run ../toggle-registration/register.sh --env invalid --appname website --errorcode 42
  [ "$status" -eq 42 ]
}

@test "should fail with code 42 when --appname is invalid and --errorcode is 42" {
  run ../toggle-registration/register.sh --env dev --appname invalidAppName --errorcode 42
  [ "$status" -eq 42 ]
}

@test "should pass with code 0 when --appname and --env are valid" {
  export GO_DEPENDENCY_LOCATOR_TEST="hbc-toggle-service/latest/Build_App/latest"
  export banner="s5a"

  run ../toggle-registration/register.sh --env qa --appname fake-service --errorcode 42
  [ "$status" -eq 0 ]
}

@test "should fail with code 42 when --env is dev, DOCKER INSTANCE is invalid and --errorcode is 42" {
# @test "should fail-safe when --env is dev, DOCKER INSTANCE is invalid and --errorcode is 42" {
  export GO_DEPENDENCY_LOCATOR_TEST="hbc-toggle-service/latest/Build_App/latest"
  export DOCKER_INSTANCE="INVALID-HOST"

  run ../toggle-registration/register.sh --env dev --appname fake-service --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}

@test "should fail with code 42 when --env is qa, banner is invalid and --errorcode is 42" {
# @test "should fail-safe when --env is qa, banner is invalid and --errorcode is 42" {
  export GO_DEPENDENCY_LOCATOR_TEST="hbc-toggle-service/latest/Build_App/latest"
  export banner="INVALID-BANNER"

  run ../toggle-registration/register.sh --env qa --appname fake-service --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}

@test "should pass when banner is s5a, --env is preview and --appname is valid service" {
  export GO_DEPENDENCY_LOCATOR_TEST="hbc-toggle-service/latest/Build_App/latest"
  export banner="s5a"

  run ../toggle-registration/register.sh --env preview --appname fake-service --errorcode 42
  [ "$status" -eq 0 ]
}

@test "should pass when banner is o5a, --env is prod and --appname is valid service" {
  export GO_DEPENDENCY_LOCATOR_TEST="hbc-toggle-service/latest/Build_App/latest"
  export banner="o5a"

  run ../toggle-registration/register.sh --env prod --appname fake-service --errorcode 42
  [ "$status" -eq 0 ]
}

@test "should pass when banner is lat, --env is prod and --appname is valid service" {
  export GO_DEPENDENCY_LOCATOR_TEST="hbc-toggle-service/latest/Build_App/latest"
  export banner="lat"

  run ../toggle-registration/register.sh --env prod --appname fake-service --errorcode 42
  [ "$status" -eq 0 ]
}

# @test "should fail-safe for --env stqa when GO_ENVIRONMENT_NAME is invalid and --appname is website" {
@test "should fail for --env stqa when GO_ENVIRONMENT_NAME is invalid and --appname is website" {
  export GO_ENVIRONMENT_NAME="INVALID-ENV-NAME"

  run ../toggle-registration/register.sh --env stqa --appname website --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}

# @test "should fail-safe for --env preview when GO_ENVIRONMENT_NAME is invalid and --appname is website" {
@test "should fail for --env preview when GO_ENVIRONMENT_NAME is invalid and --appname is website" {
  export GO_ENVIRONMENT_NAME="INVALID-ENV-NAME"

  run ../toggle-registration/register.sh --env preview --appname website --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}

# @test "should fail-safe for --env prod when GO_ENVIRONMENT_NAME is invalid and --appname is website" {
@test "should fail for --env prod when GO_ENVIRONMENT_NAME is invalid and --appname is website" {
  export GO_ENVIRONMENT_NAME="INVALID-ENV-NAME"

  run ../toggle-registration/register.sh --env prod --appname website --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}

# @test "should fail-safe for --env stqa when BUILD_TASK_NAME is invalid and --appname is contact-center" {
@test "should fail for --env stqa when BUILD_TASK_NAME is invalid and --appname is contact-center" {
  export BUILD_TASK_NAME="INVALID-TASK-NAME"

  run ../toggle-registration/register.sh --env stqa --appname contact-center --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}

# @test "should fail-safe for --env dev when BUILD_TASK_NAME is invalid and --appname is contact-center" {
@test "should fail for --env dev when BUILD_TASK_NAME is invalid and --appname is contact-center" {
  export BUILD_TASK_NAME="INVALID-TASK-NAME"

  run ../toggle-registration/register.sh --env dev --appname contact-center --errorcode 42
  [ "$status" -eq 42 ]
  # [ "$status" -eq 0 ]
}
