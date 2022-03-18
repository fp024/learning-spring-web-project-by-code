#!/bin/sh
HOME_DIR=~/git-fp024/learning-spring-web-project-by-code
cd $HOME_DIR/latest
export $(grep -v '^#' $HOME_DIR/env.properties | xargs)
nohup $HOME_DIR/latest/mvnw clean jetty:run 1>$HOME_DIR/log/board.log 2>&1 &
