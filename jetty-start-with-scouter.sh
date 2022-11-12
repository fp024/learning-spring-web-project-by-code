#!/bin/sh
PROJECT_ROOT=/home/fp024/git-fp024/learning-spring-web-project-by-code
export $(grep -v '^#' ${PROJECT_ROOT}/setenv.properties | xargs)
export MAVEN_OPTS="-Dscouter.agent.lib=${SCOUTER_JAVA_AGENT_LIB} -Dscouter.config.file=${SCOUTER_JAVA_AGENT_CONF}"
cd ${PROJECT_ROOT}/${LATEST_PROJECT_HOME} 
nohup ./mvnw clean jetty:run -Pwebapp-run-with-scouter 1>${PROJECT_ROOT}/log/board.log 2>&1 &
${PROJECT_ROOT}/show-log.sh
