## Spring data playground (mongo)

This project is a SpringBoot-data CRUD using MongoDB, and it represents
a compromise in the use of different libraries working together in the
most seamless way to avoid boilerplate code whilst maintaining ease of
use and of configuration.

It provides standard CRUD functionalities over the model as well as a
basic textual search.

Mongo is assumed to be running, its host and port to be specified
in this application's yml file.
The integration tests are implemented as a BDD Cucumber feature.
They make use of the Spring-Cucumber integration to launch the webapp
and interact with the APIs.

The (denormalised) model is mostly contained in a class with nested inner
classes (static, to be Jackson-compatible).

A basic HAL support has been provided.
Libraries such as Spring HATEOAS or Spring Data Rest might be considered
for further hyperlink extensions.

The /payments endpoint supports pagination with the parameters
`?page=0&size=3` (this is not shown in the Springfox documentation, due
to a non-perfect interaction between Springfox and Spring data).

The API documentation is exposed via Springfox Swagger
on `http://<host:port>/swagger-ui.html`.

The project could have been based on SpringBoot 2 (webflux),
but swagger wouldn't work:
`https://www.bountysource.com/issues/47763045-springfox-doesn-t-work-with-spring-boot-2-0-and-spring-data-kay-rc2`

Launch with `mvn clean spring-boot:run`, test with `mvn clean test`.