#!/bin/bash

cd src
javac -cp . -d ../bin adt/Main.java adt/exceptions/*.java adt/flexible/*.java

cd ../bin
java -cp . adt.Main