package com.example.pixelwarserver;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


@EnableMongoRepositories(basePackages = "com.example.pixelwarserver.repository")
@SpringBootApplication
public class PixelWarServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixelWarServerApplication.class, args);
    }

    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb+srv://juvetquentin19:pHwT4UVrlS0xXOPv@cluster0.ouqpp0e.mongodb.net/?retryWrites=true&w=majority";

        ConnectionString connectionStr = new ConnectionString(connectionString);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionStr)
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "pixelwar_db");
    }

}