package playground;


import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import playground.config.UnirestMapperConfig;

@SpringBootApplication
@EnableWebFlux
public class App {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(App.class);
//    }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @PostConstruct
  public void setupUnirest() {
    UnirestMapperConfig.setup();
  }

}