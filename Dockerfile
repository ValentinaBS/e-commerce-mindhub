FROM gradle:8.0.2-jdk11-alpine

COPY src .

EXPOSE 8080

RUN gradle build

ENTRYPOINT ["java", "-jar", "build/libs/homebanking-0.0.1-SNAPSHOT.jar"]