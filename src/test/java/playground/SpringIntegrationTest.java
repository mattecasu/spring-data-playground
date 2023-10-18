package playground;

import org.junit.platform.suite.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static io.cucumber.core.options.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("playground")
@ConfigurationParameters({
  @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "json:build/cucumber/cucumber.json"),
  @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "playground")
})
public class SpringIntegrationTest {

  @Autowired protected WebTestClient webTestClient;
  protected String baseUrl = "http://localhost:8080";
}
