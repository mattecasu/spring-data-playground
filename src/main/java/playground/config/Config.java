package playground.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootConfiguration
public class Config extends AbstractReactiveMongoConfiguration {


  @Value("${mongo.host}")
  private String host;

  @Value("${mongo.port}")
  private String port;

  @Value("${mongo.db.name}")
  private String dbName;

//  public static PropertyNamingStrategy fieldNamesStrategy = new PropertyNamingStrategy.SnakeCaseStrategy();

//  @Bean
//  public ObjectMapper jacksonObjectMapper() {
//    return new ObjectMapper().setPropertyNamingStrategy(fieldNamesStrategy);
//  }

  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create("mongodb://" + host + ":" + Integer.valueOf(port));
  }

  @Override
  protected String getDatabaseName() {
    return dbName;
  }

  @Bean
  public ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
  }


}
