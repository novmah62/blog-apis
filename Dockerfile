FROM openjdk:17-jdk

WORKDIR /app

COPY target/*.jar /app/blog-apis.jar

EXPOSE 8080

CMD ["java", "-jar", "blog-apis.jar"]