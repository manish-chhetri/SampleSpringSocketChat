package com.example.SampleSpringSocketChatApplication.handler;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChatMessageHandler extends TextWebSocketHandler {
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        webSocketSessions.add(session);
        System.out.println("established connection");
        System.out.println(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
        System.out.println(session.getId() + " Connection closed");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        super.handleMessage(session, message);
        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
        System.out.println(session.getId() + " sending message:: " + message.toString());
    }

    /*@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(payload);
        session.sendMessage(new TextMessage("Hi " + jsonObject.get("user") + " how may we help you?"));
        System.out.println(jsonObject.get("user"));
    }*/

}
