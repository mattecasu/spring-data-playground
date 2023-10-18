package playground;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("playground")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "playground")
public class SpringIntegrationTest {

  @Autowired protected WebTestClient webTestClient;
  protected String baseUrl = "http://localhost:8080";
}
