FROM gradle:8.0.2-jdk11-alpine

COPY . .

RUN gradle build

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/e-commerce-0.0.1-SNAPSHOT.jar"]