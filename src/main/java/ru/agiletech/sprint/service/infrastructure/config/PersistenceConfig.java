package ru.agiletech.sprint.service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories("ru.agiletech.sprint.service.infrastructure.persistence")
public class PersistenceConfig {

    @Bean
    public MongoTransactionManager transactionManager(MongoDbFactory mongoDbFactory){
        return new MongoTransactionManager(mongoDbFactory);
    }

}
