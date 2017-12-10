package playground;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest(
        classes = App.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public abstract class SpringIntegrationTest {

    String baseUrl = "http://localhost:8080";

}