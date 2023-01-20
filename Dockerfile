FROM openjdk:17
EXPOSE 5500
COPY . .
ADD build/libs/money-transfer-1.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]