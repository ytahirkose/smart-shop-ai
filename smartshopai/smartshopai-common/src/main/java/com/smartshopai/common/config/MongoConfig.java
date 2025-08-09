package com.smartshopai.common.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.concurrent.TimeUnit;

/**
 * MongoDB configuration for SmartShopAI application
 * Provides MongoDB client, template, and validation setup
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.smartshopai")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host:localhost}")
    private String host;

    @Value("${spring.data.mongodb.port:27017}")
    private int port;

    @Value("${spring.data.mongodb.database:smartshopai}")
    private String database;

    @Value("${spring.data.mongodb.username:}")
    private String username;

    @Value("${spring.data.mongodb.password:}")
    private String password;

    @Value("${spring.data.mongodb.auto-index-creation:true}")
    private boolean autoIndexCreation;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString;
        
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            connectionString = new ConnectionString(
                String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin",
                    username, password, host, port, database)
            );
        } else {
            connectionString = new ConnectionString(
                String.format("mongodb://%s:%d/%s", host, port, database)
            );
        }

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applyToSocketSettings(builder -> 
                builder.connectTimeout(5000, TimeUnit.MILLISECONDS)
                       .readTimeout(30000, TimeUnit.MILLISECONDS))
            .applyToServerSettings(builder -> 
                builder.heartbeatFrequency(10000, TimeUnit.MILLISECONDS))
            .applyToClusterSettings(builder -> 
                builder.serverSelectionTimeout(5000, TimeUnit.MILLISECONDS))
            .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), getDatabaseName());
        
        // Enable auto-index creation if configured
        if (autoIndexCreation) {
            mongoTemplate.setWriteResultChecking(org.springframework.data.mongodb.core.WriteResultChecking.EXCEPTION);
        }
        
        return mongoTemplate;
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Override
    protected boolean autoIndexCreation() {
        return autoIndexCreation;
    }
}
