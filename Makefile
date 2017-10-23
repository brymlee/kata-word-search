SOURCE_MAIN = src/main/java
SOURCE_TEST = src/test/java
BASE_PACKAGE = kata/word/search
GUAVA_LIBRARY = lib/guava-23.0.jar
JUNIT_LIBRARY = lib/junit-4.12.jar
HAMCREST_LIBRARY = lib/hamcrest-all-1.3.jar
LIBRARIES = ${GUAVA_LIBRARY}:${JUNIT_LIBRARY}:${HAMCREST_LIBRARY}
CLASSPATH = ${SOURCE_MAIN}:${SOURCE_TEST}:${LIBRARIES}
all:
	javac -cp "${CLASSPATH}" ${SOURCE_MAIN}/${BASE_PACKAGE}/*.java 
	javac -cp "${CLASSPATH}" ${SOURCE_TEST}/${BASE_PACKAGE}/*.java

run:
	java -cp "${CLASSPATH}" kata.word.search.WordSearchView

test:
	java -cp "${CLASSPATH}" org.junit.runner.JUnitCore kata.word.search.WordSearchTest
	java -cp "${CLASSPATH}" org.junit.runner.JUnitCore kata.word.search.FindWordTest
