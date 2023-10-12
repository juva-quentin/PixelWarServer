package com.example.pixelwarserver;

import com.example.pixelwarserver.model.Pixel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.pixelwarserver.controller.PixelController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;


/**
 * Gestionnaire des connexions WebSocket.
 * Cette classe gère les connexions WebSocket, les messages entrants et sortants,
 * et maintient une liste des sessions actives pour envoyer des mises à jour à tous les clients.
 */
@Log4j2
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private PixelController pixelController;

    private final List<WebSocketSession> activeSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String sessionId = session.getId();
        log.info("Nouvelle connexion WebSocket établie : " + sessionId);
        activeSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("Connexion WebSocket fermée : " + session.getId());

        activeSessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        if (jsonNode.has("type")) {
            String messageType = jsonNode.get("type").asText();

            if ("updatePixel".equals(messageType)) {
                Pixel pixel = objectMapper.convertValue(jsonNode.get("data"), Pixel.class);
                pixelController.updatePixel(pixel);
                List<Pixel> response = pixelController.getAllPixels();
                String jsonResponse = objectMapper.writeValueAsString(response);
                TextMessage responseMessage = new TextMessage(jsonResponse);
                sendToActiveSessions(responseMessage);
            } else if ("getAllPixels".equals(messageType)) {
                List<Pixel> response = pixelController.getAllPixels();
                String jsonResponse = objectMapper.writeValueAsString(response);
                TextMessage responseMessage = new TextMessage(jsonResponse);
                sendToActiveSessions(responseMessage);
            }
        }
    }

    /**
     * Envoie un message WebSocket à toutes les sessions actives.
     *
     * @param message Le message à envoyer.
     */
    public void sendToActiveSessions(TextMessage message) {
        for (WebSocketSession session : activeSessions) {
            try {
                session.sendMessage(message);
            } catch (Exception e) {
                log.error("Erreur lors de l'envoi d'un message WebSocket : " + e.getMessage());
                activeSessions.remove(session);
            }
        }
    }

}
