FROM gradle:latest AS build

WORKDIR /home/sms

COPY build.gradle settings.gradle ./

COPY src ./src

RUN gradle build -x test

EXPOSE 8080

ENTRYPOINT ["gradle", "bootRun"]
