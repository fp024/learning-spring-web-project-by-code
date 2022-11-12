#!/bin/sh
PROJECT_ROOT=~/git-fp024/learning-spring-web-project-by-code
export $(grep -v '^#' ${PROJECT_ROOT}/setenv.properties | xargs)
cd ${PROJECT_ROOT}/${LATEST_PROJECT_HOME}
./mvnw jetty:stop
