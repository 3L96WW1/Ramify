#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
export GRADLE_USER_HOME="$PWD/.gradle-home"
args=("$@")
if [[ ! " ${args[*]} " =~ " --no-daemon " ]]; then
  args+=(--no-daemon)
fi
exec ./gradlew "${args[@]}"
