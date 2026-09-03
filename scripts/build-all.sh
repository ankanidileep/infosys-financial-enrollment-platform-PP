#!/usr/bin/env bash
set -euo pipefail
if [[ -d /usr/lib/jvm/java-21-amazon-corretto.x86_64 ]]; then export JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto.x86_64; fi
export PATH="$JAVA_HOME/bin:$PATH"
java -version; javac -version; mvn -version
if ! java -version 2>&1 | grep -q 'version "21'; then echo 'ERROR: Java 21 is required.' >&2; exit 1; fi
for d in services/*; do echo "Building $d"; (cd "$d" && mvn -B clean test package); done
