FROM amazoncorretto:17-alpine3.20-jdk



RUN apk update
RUN apk add prometheus-node-exporter

EXPOSE 8080
EXPOSE 9100


ARG JAR_FILE=build/libs/coffee-shop.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["sleep","3600"]