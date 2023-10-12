package com.example.pixelwarserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration WebSocket pour gérer les connexions WebSocket.
 * Cette classe configure le gestionnaire WebSocket et les points de terminaison.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    /**
     * Gestionnaire WebSocket pour gérer les connexions et les messages WebSocket.
     */
    @Autowired
    protected WebSocketHandler webSocketHandler;


    /**
     * Enregistre les gestionnaires WebSocket et les points de terminaison.
     *
     * @param registry Le registre des gestionnaires WebSocket.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/pixelwar").setAllowedOrigins("*");
    }

}