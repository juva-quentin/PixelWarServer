package com.example.pixelwarserver;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Application principale de PixelWar Server.
 * Cette classe démarre l'application Spring Boot et configure la connexion MongoDB,
 * ainsi que la configuration WebSocket.
 */

@EnableMongoRepositories(basePackages = "com.example.pixelwarserver.repository")
@SpringBootApplication
public class PixelWarServerApplication {

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.pswd}")
    private String databasePswd;

    /**
     * Point d'entrée de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SpringApplication.run(PixelWarServerApplication.class, args);
    }

    /**
     * Configuration de la connexion MongoDB en utilisant les paramètres de connexion.
     *
     * @return Une instance de MongoClient configurée pour se connecter à MongoDB.
     */
    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb+srv://" + databaseName + ":" + databasePswd + "@cluster0.ouqpp0e.mongodb.net/?retryWrites=true&w=majority";

        ConnectionString connectionStr = new ConnectionString(connectionString);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionStr)
                .build();

        return MongoClients.create(settings);
    }

    /**
    * Configuration de l'accès à la base de données MongoDB via MongoTemplate.
    *
    * @return Une instance de MongoTemplate pour interagir avec MongoDB.
    */
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "pixelwar_db");
    }

}