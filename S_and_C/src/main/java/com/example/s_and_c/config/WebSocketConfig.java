package com.example.s_and_c.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // Prefix for broadcasting messages
        config.setApplicationDestinationPrefixes("/app");  // Prefix for client-to-server communication
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/**")  // This is the WebSocket endpoint
                .setAllowedOrigins("http://localhost:5173")  // Allow frontend origin (localhost:63342)
                .withSockJS();  // Enable SockJS for fallback support
    }
}
