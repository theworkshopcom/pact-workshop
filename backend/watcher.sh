#!/bin/sh

until [ -f /src/target/backend-0.0.1-SNAPSHOT.jar ]
do
     sleep 5
done

function reload() {
    echo "Jar changed, reloading app..."
    killall java
    java -jar /src/target/backend-0.0.1-SNAPSHOT.jar &
}

java -jar /src/target/backend-0.0.1-SNAPSHOT.jar &
inotifywait -e close_write,moved_to,create -m /src/target | while read -r directory events filename; do
  if [ "$filename" = "backend-0.0.1-SNAPSHOT.jar" ]; then
    reload
  fi
done