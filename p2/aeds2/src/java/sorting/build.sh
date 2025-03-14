#!/bin/bash

cd src
javac -cp . -d ../bin sorting/Main.java sorting/lib/*.java

cd ../bin
java -cp . sorting.Main