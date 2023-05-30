#!/bin/bash
 while true
do
    pid=`ps -ef | grep highway-scheduling-manage-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}'`
    if [ -z "$pid" ]; then
        echo "highway-scheduling-manage-1.0-SNAPSHOT.jar is not running. Restarting..."
        nohup java -jar highway-scheduling-manage-1.0-SNAPSHOT.jar > highway-scheduling-manage.out 2>&1 &
    fi
    sleep 10
done