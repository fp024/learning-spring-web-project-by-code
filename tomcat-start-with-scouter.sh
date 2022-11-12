#!/bin/sh
PROJECT_ROOT=/home/fp024/git-fp024/learning-spring-web-project-by-code
export $(grep -v '^#' ${PROJECT_ROOT}/setenv.properties | xargs)
export MAVEN_OPTS="-Dscouter.agent.lib=${SCOUTER_JAVA_AGENT_LIB} -Dscouter.config.file=${SCOUTER_JAVA_AGENT_CONF}" 
cd ${PROJECT_ROOT}/${LATEST_PROJECT_HOME}

# nohup ./mvnw clean package -DskipTests cargo:run -Pwebapp-run-with-scouter 1>${PROJECT_ROOT}/log/board.log 2>&1 &
# ${PROJECT_ROOT}/show-log.sh
#
# cargo:run으로 실행하면 Ctrl+C 입력 감지를 위한 별도 프로세스가 뜨는 것으로 보이는데, 이 프로세스가 cargo:stop으로 종료되지 않는다.
# nohup로 백그라운드실행을 여러번하다보면 프로세스가 쌓일 수 있음. 
#
# Tomcat은 nohup로 백그라운드 실행하지말고 직접 실행하는 것이 낫겠다.

./mvnw clean package -DskipTests cargo:run -Pwebapp-run-with-scouter
