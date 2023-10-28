./build.sh

echo "--- Running"

PROGRAM=${1:-"Main"}
java -cp build $PROGRAM