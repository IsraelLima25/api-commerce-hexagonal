FROM openjdk:17
WORKDIR /app
COPY ./target/commerce-1.0.0-SNAPSHOT.jar /app/
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=default", "/apps/commerce-1.0.0-SNAPSHOT.jar" ]