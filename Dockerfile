FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD build/libs/task1-1.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]