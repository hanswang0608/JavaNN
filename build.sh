echo --- Cleaning

rm -f *.jar
rm -f *.class
rm -fr build

echo --- Compiling Java
javac *.java -d build

echo --- Building Jar
jar cfe JavaNN.jar Trainer build/*.class