#!/bin/bash
count=`ps aux | grep 'java' | grep -v 'grep' -c`
if [ $count -eq 0 ]; then
        now=`date +%F\ %T`
        echo "[$now] Java is offline, try to restart..." >> /app/check_process.log
        nohup /root/.sdkman/candidates/java/21.0.2-amzn/bin/java -Duser.dir=/app -jar /app/lite-note-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --spring.config.import=optional:file:/app/ >/dev/null 2>&1 &
else
        now=`date +%F\ %T`
        echo "[$now] Java is online, everything seems to be OK..." >> /app/check_process.log
fi
