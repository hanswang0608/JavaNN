#!/bin/sh
./clean.sh

echo --- Compiling Java
javac src/*/*/*.java -d bin

echo "--- Running"

PROGRAM=${1:-"JavaNN.Training.Trainer"}
java -cp bin $PROGRAM