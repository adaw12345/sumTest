set CLASSPATH=%CLASSPATH%;libs\JPack.jar
md bin
copy src/Delivery.xml bin/Delivery.xml
javac -d bin src/*.java
cd bin
java -cp ./;..\libs\JPack.jar MainWindow
cd..
pause..