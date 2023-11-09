#!/bin/sh
./clean.sh

echo --- Compiling Java
javac src/*/*/*.java -d bin

# echo --- Building Jar
# jar cfe JavaNN.jar Trainer build/*.class