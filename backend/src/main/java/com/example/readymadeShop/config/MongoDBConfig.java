package com.example.readymadeShop.config;

// src/main/java/com/example/readymadeShop/config/MongoDBConfig.java

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.readymadeShop.repository")
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "readymadeShopDB";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
