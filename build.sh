#!/bin/sh
./clean.sh

echo --- Compiling Java
javac src/*/*/*.java -d bin

echo --- Building Jar
cd bin
jar cfe ../JavaNN.jar JavaNN.Training.Trainer ./*/*/*.class