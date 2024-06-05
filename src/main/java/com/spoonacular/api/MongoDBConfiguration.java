package com.spoonacular.api;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * The MongoDBConfiguration class is a configuration class for MongoDB in this Spring Boot application.
 * The class is annotated with @Configuration, indicating that it's a configuration class that may have @Bean annotated methods.
 * The class includes a private field named connectionString which is annotated with @Value.
 * The @Value annotation indicates that the field should be populated with the value of the 'spring.data.mongodb.uri' property from the application's configuration.
 * The class includes a method named mongoClient which is annotated with @Bean.
 * The @Bean annotation indicates that the method produces a bean that should be managed by the Spring container.
 * The mongoClient method creates and returns a MongoClient object.
 * The MongoClient object is configured with a connection string and a codec registry.
 * The codec registry is configured to include the default codec registry and a POJO codec registry.
 * The POJO codec registry is configured to automatically map POJOs.
 */
@Configuration
public class MongoDBConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Bean
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder()
                                                                         .automatic(true)
                                                                         .build());

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        return MongoClients.create(MongoClientSettings.builder()
                                                      .applyConnectionString(new ConnectionString(connectionString))
                                                      .codecRegistry(codecRegistry)
                                                      .build());
    }

}
