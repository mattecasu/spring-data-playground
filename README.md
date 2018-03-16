## Spring data playground (mongo)

This project is a SpringBoot-data CRUD using MongoDB, and it represents
a compromise in the use of different libraries working together in the
most seamless way to avoid boilerplate code whilst maintaining ease of
use and of configuration.

It provides standard CRUD functionalities over the model as well as a
basic textual search.

Mongo is assumed to be running, its host and port to be specified
in this application's yml file.
Install and launch on Mac:
```
brew install mongodb
brew services start mongodb

```

The integration tests are implemented as a BDD Cucumber feature.
They make use of the Spring-Cucumber integration to launch the webapp
and interact with the APIs.

The API documentation (will be) exposed via Springfox Swagger
on `http://<host:port>/swagger-ui.html`.

About swagger support, keep an eye on:
`https://github.com/springfox/springfox/issues/1773`

The endpoints are CORS-enabled.

Launch with `mvn clean spring-boot:run`, test with `mvn clean test`.