FROM openjdk:17-jdk-slim

COPY target/investment_project-1.0-SNAPSHOT.jar /app/investment_project.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/investment_project.jar"]