FROM openjdk:17
ARG JAR_FILE
ENV name=$JAR_FILE
WORKDIR /app
COPY ./target/${JAR_FILE} /app/
ENTRYPOINT java -jar -Dspring.profiles.active=default /app/$name