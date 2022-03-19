#!/bin/sh
HOME_DIR=~/git-fp024/learning-spring-web-project-by-code
export $(grep -v '^#' $HOME_DIR/env.properties | xargs)
cd $HOME_DIR/$LATEST
$HOME_DIR/latest/mvnw clean jetty:stop 1>$HOME_DIR/log/board.log 2>&1 
