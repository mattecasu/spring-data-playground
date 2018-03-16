package playground;

import static com.mashape.unirest.http.Unirest.delete;
import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static com.mashape.unirest.http.Unirest.put;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static playground.config.UnirestMapperConfig.getUnirestObjectMapper;
import static playground.model.MockPayments.mockPayment;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import playground.model.MockPayments;
import playground.model.Payment;

@Slf4j
public class StepDefs extends SpringIntegrationTest {

  private String url = super.baseUrl + "/payments";
  private HttpResponse<JsonNode> response;
  private String lastId;

  @Autowired
  private WebTestClient webTestClient;

  @Given("^the db is clean$")
  public void dbClean() throws Throwable {
    assertTrue(delete(url)
        .asJson().getStatus() == 200);
  }

  @And("^the client POST (\\d+) (?:mock|mocks)$")
  public void mockNData(Integer n) {
    MockPayments.getMockPayments(n).forEach(
        mock -> {
          try {
            response = post(url)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(mock.setId(null))
                .asJson();
            lastId = response.getBody().getObject().getString("id");
          } catch (UnirestException | JSONException e) {
            log.error(e.getMessage());
          }
        }
    );
  }

  @And("^the client reactively POST 1 mock$")
  public void mockNRData() {

    ResponseSpec reactiveRes = webTestClient.post()
        .syncBody(mockPayment().setId(null))
        .header("Content-Type", "application/json")
        .exchange();

    reactiveRes
        .expectBody(Payment.class)
        .consumeWith(res -> {
          String id = res.getResponseBody().getId();
          Assertions.assertThat(res.getResponseBody().equals(mockPayment().setId(id)));
        });
  }

  @And("^the client receives the POSTed object with id$")
  public void verifyPostedMock() {
    assertThat(
        getUnirestObjectMapper().readValue(response.getBody().toString(), Payment.class),
        is(equalTo(mockPayment().setId(lastId)))
    );
  }


  @And("^the client POST a mock with id$")
  public void mockDataWithId() throws Throwable {
    Payment mock = mockPayment();
    response = post(url)
        .header("Content-Type", "application/json")
        .body(mock)
        .asJson();
  }

  @And("^the client updates a payment with value \"([^\"]*)\" on the type field$")
  public void update(String value) throws Throwable {
    Payment update = mockPayment().setId(lastId).setType(value);
    response = put(url + "/" + lastId)
        .header("Content-Type", "application/json")
        .body(update)
        .asJson();
  }

  @And("^the response contains value \"([^\"]*)\" on the type field$")
  public void checkValue(String value) throws Throwable {
    assertThat(response.getBody().getObject().getString("type"), is(equalTo(value)));
  }

  @And("^the client updates a payment with no id$")
  public void updateNoId() throws Throwable {
    Payment update = mockPayment().setId(null);
    response = put(url + "/" + lastId)
        .header("Content-Type", "application/json")
        .body(update)
        .asJson();
  }

  @And("^the client DELETE a payment$")
  public void deletePayment() throws Throwable {
    response = delete(url + "/" + lastId).asJson();
  }


  @When("^the client GET /payments$")
  public void clientGetsPayments() throws Throwable {
    response = get(url).asJson();
  }

  @When("^the client GET /payments with page (\\d+) and size (\\d+)$")
  public void clientGetsPayments(int page, int size) throws Throwable {
    response = get(url)
        .queryString("page", page)
        .queryString("size", size)
        .asJson();
  }

  @When("^the client GET a payment$")
  public void clientGetsPayment() throws Throwable {
    response = get(url + "/" + lastId).asJson();
  }

  @Then("^the client receives status code of (\\d+)$")
  public void clientReceivesCode(int statusCode) {
    assertThat(response.getStatus(), is(equalTo(statusCode)));
  }

  @And("^the client receives (\\d+) payments$")
  public void clientReceivesArrayOfLength(int size) throws JSONException {
    assertTrue(getResponseLength() == size);
  }

  @When("^the client GET /payments with beneficiaryName \"([^\"]*)\"$")
  public void clientGetsPayments(String value) throws Throwable {
    response = get(url)
        .queryString("beneficiary", value)
        .asJson();
  }


  private int getResponseLength() {
    return response.getBody().getArray().length();
  }

}