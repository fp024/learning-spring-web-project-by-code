#!/bin/sh
HOME_DIR=~/git-fp024/learning-spring-web-project-by-code
export $(grep -v '^#' $HOME_DIR/env.properties | xargs)
tail -n 50 -f $HOME_DIR/log/board.log
