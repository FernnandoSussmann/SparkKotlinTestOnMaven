# SparkKotlinTestOnMaven
Same as [SparkKotlinTest](https://github.com/FernnandoSussmann/SparkKotlinTest) but running on maven because of some issues with gradle when compilling

## Why
Gradle simply does not compile the code. It keeps stuck for more than 1 hour on "Compiling kotlin" step. And also Intellij freezes if by simply opening the project when you have `spark.udf()` inside your code.

## Impressions

I will keep this session only in the previous project to make it unique.

## How to run
On project folder run
```
mvm install
mvn package
java -jar target/SparkKotlinTestOnMaven-1.0-SNAPSHOT-jar-with-dependencies.jar
```
