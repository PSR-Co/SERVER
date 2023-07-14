#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/psr
cd $REPOSITORY

APP_NAME=psr
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다." >> $REPOSITORY/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> $REPOSITORY/deploy.log
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH " >> $REPOSITORY/deploy.log
nohup java -jar $JAR_PATH
