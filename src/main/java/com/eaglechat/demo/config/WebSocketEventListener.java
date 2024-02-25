package com.eaglechat.demo.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.eaglechat.demo.chat.ChatMessage;
import com.eaglechat.demo.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j

// this slf4j is for logging user info when user leave chat

public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;



    // when user disconnects then we need to inform remaining person that , this speciific user is disconnected so 
    // that's why we are creating this even listener to listen the session disconnect server

    @EventListener
    public void handleWebSocketDisconnectListener( SessionDisconnectEvent event){

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);

            // chatmessage tells basically ki user is left to all currently connected users
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }



    }


    
    
}
