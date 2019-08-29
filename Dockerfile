FROM openjdk:12
WORKDIR /usr/src/social-api-source-code
COPY . /usr/src/social-api-source-code/
RUN ./mvnw clean verify

WORKDIR /usr/src/social-api
RUN cp /usr/src/social-api-source-code/target/*.jar ./app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]