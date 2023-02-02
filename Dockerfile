FROM eclipse-temurin:17-jdk
COPY target/wordsearch-api-0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java","-jar","/app.jar"]