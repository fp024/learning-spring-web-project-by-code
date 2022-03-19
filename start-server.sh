#!/bin/sh
HOME_DIR=~/git-fp024/learning-spring-web-project-by-code
export $(grep -v '^#' $HOME_DIR/env.properties | xargs)
cd $HOME_DIR/$LATEST_HOME
nohup $HOME_DIR/$LATEST_HOME/mvnw clean jetty:run 1>$HOME_DIR/log/board.log 2>&1 &
