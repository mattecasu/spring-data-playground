package playground.steps;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static playground.model.MockPayments.mockPayment;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import playground.SpringIntegrationTest;
import playground.model.MockPayments;
import playground.model.Payment;
import reactor.core.publisher.Mono;

@Slf4j
public class StepDefs extends SpringIntegrationTest {

    private String url = super.baseUrl + "/payments";
    private ResponseSpec responseSpec;
    private String lastId;
    

    @Given("^the db is clean$")
    public void dbClean() {
        webTestClient
                .delete()
                .uri(url)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @And("^the client POST (\\d+) (?:mock|mocks)$")
    public void mockNData(Integer n) {
        MockPayments.getMockPayments(n)
                .forEach(
                        mock ->
                                lastId =
                                        webTestClient
                                                .post()
                                                .uri(url)
                                                .contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON)
                                                .body(Mono.just(mock.setId(null)), Payment.class)
                                                .exchange()
                                                .expectStatus()
                                                .isOk()
                                                .expectBody(Payment.class)
                                                .returnResult()
                                                .getResponseBody()
                                                .getId());
    }

    @And("^the client POST a mock with id$")
    public void mockDataWithId() {
        Payment mock = mockPayment();
        responseSpec =
                webTestClient
                        .post()
                        .uri(url)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .body(Mono.just(mock), Payment.class)
                        .exchange();
    }

    @And("^the client updates a payment with value \"([^\"]*)\" on the type field$")
    public void update(String value) {

        Payment update = mockPayment().setId(lastId).setType(value);

        webTestClient
                .put()
                .uri(url + "/" + lastId)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(update), Payment.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id")
                .isEqualTo(lastId)
                .jsonPath("$.type")
                .isEqualTo(value);
    }

    @And("^the client updates a payment with no id$")
    public void updateNoId() {

        Payment update = mockPayment().setId(null);

        responseSpec =
                webTestClient
                        .put()
                        .uri(url + "/" + lastId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .body(Mono.just(update), Payment.class)
                        .exchange();
    }

    @And("^the client DELETE a payment$")
    public void deletePayment() {
        responseSpec = webTestClient.delete().uri(url + "/" + lastId).exchange();
    }

    @When("^the client GET /payments$")
    public void clientGetsPayments() {
        responseSpec = webTestClient.get().uri(url).exchange();
    }

    @When("^the client GET a payment$")
    public void clientGetsPayment() {
        responseSpec =
                webTestClient.get().uri(url + "/" + lastId)
                        .accept(APPLICATION_JSON)
                        .exchange();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void clientReceivesCode(int statusCode) {
        responseSpec.expectStatus().isEqualTo(statusCode);
    }

    @And("^the client receives (\\d+) payments$")
    public void clientReceivesArrayOfLength(int size) {
        responseSpec.expectBodyList(Payment.class).hasSize(size);
    }

    @When("^the client GET /payments with beneficiaryName \"([^\"]*)\"$")
    public void clientGetsPayments(String value) {
        responseSpec = webTestClient.get().uri(url + "?beneficiary=" + value).exchange();
    }
}
