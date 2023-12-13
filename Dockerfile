FROM openjdk:17-alpine

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apk --no-cache add tzdata

COPY build/libs/psr-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]