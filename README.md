## Spring data playground (mongo)

This project is a SpringBoot-data reactive CRUD using MongoDB.

It provides standard CRUD functionalities over the model as well as a
basic textual search.

The integration tests are implemented as end-to-end BDD Cucumber feature.
They use docker compose to pull and run a mongo docker image.
They use the Spring-Cucumber integration to launch the webapp
and interact with the APIs.

As of yet, Springfox is not yet compatible with webflux:
`https://github.com/springfox/springfox/issues/1773`.
When a fix is available, the API documentation (will be)
exposed via Springfox Swagger on
`http://<host:port>/swagger-ui.html`.

The endpoints are CORS-enabled.

Launch with `gradle clean bootRun`, test with `gradle clean test`.