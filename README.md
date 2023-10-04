# StudentManagementSystem

Student management system written with Spring Boot. Made to learn technologies like GraphQL, MongoDB and Gradle.
Application consists of graphql api only for now but I may add frontend in the future.

## How to use

* To run with `docker` you have to install `docker` and `docker-compose` on your machine.

* Start spring boot server and mongodb database. Mongo database is recommended to be used within docker
  container which is part of `docker-compose.yml` using:

```bash
docker compose up -d
```

* If you want to use it with gradle just run:

```gradle
./gradlew bootRun
```

* Your api will be available on `localhost:8080/graphql`,

* There is also interactive `graphql` playground on `localhost:8080/graphiql`

## Technology stack

* Java 17,
* Spring Boot,
* Gradle Groovy,
* GraphQL,
* MongoDB,
* Mongo Express,
* Docker,
* Jwt,
* Postman,
* Junit,
* Mapstruct,
* Mockito
