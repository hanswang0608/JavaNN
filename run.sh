#!/bin/sh
./build.sh

echo "--- Running"

PROGRAM=${1:-"JavaNN.Training.Trainer"}
java -cp bin $PROGRAM