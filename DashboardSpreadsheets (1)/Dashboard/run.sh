#!/bin/bash

cd `dirname $0`

export CLASSPATH=$CLASSPATH:./libs/JPack.jar
mkdir ./bin
cp ./src/Delivery.xml ./bin/Delivery.xml
javac -d ./bin ./src/*.java
cd bin
java -cp ./:./../libs/JPack.jar MainWindow
cd ..
echo “Done”