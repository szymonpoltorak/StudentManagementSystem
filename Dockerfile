FROM gradle:8.3.0-jdk17-alpine

WORKDIR /home/sms

COPY build.gradle settings.gradle ./

COPY src ./src

RUN gradle build -x test

EXPOSE 8080

ENTRYPOINT ["gradle", "bootRun"]
