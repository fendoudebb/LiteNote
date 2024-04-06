#!/bin/bash
count=`ps aux | grep 'java' | grep -v 'grep' -c`
if [ $count -eq 0 ]; then
        now=`date +%F\ %T`
        echo "[$now] Java is offline, try to restart..." >> /app/check_java.log
        nohup java -jar /app/lite-note-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod >/dev/null 2>&1 &
else
        now=`date +%F\ %T`
        echo "[$now] Java is online, everything seems to be OK..." >> /app/check_java.log
fi
