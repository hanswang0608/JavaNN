echo --- Cleaning

rm -f *.jar
rm -f *.class
rm -fr build

echo --- Compiling Java
javac *.java -d build