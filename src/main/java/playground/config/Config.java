package playground.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootConfiguration
@EnableMongoRepositories("playground.repo")
public class Config extends AbstractMongoConfiguration {


    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private String port;

    @Value("${mongo.db.name}")
    private String dbName;

    public static PropertyNamingStrategy fieldNamesStrategy = new PropertyNamingStrategy.SnakeCaseStrategy();

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper().setPropertyNamingStrategy(fieldNamesStrategy);
    }

    @Override
    public MongoClient mongo() {
        return new MongoClient(host, Integer.valueOf(port));
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

}
