From openjdk:21-jdk-slim
WORKDIR /app
COPY target/clearExtract-Service-0.0.1-SNAPSHOT.jar app.jar
ENV DB_HOST=34.61.250.237 \
    DB_PORT=5432 \
    DB_USER=postgres \
    DB_PASS=postgres
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]



