package com.spring.manshubapi.config.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 구성
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트에서 구독하는 메시지 경로를 설정 (예: "/topic")
        config.enableSimpleBroker("/topic");

        // 클라이언트가 메시지를 보낼 때 사용할 경로 설정 (예: "/app")
        config.setApplicationDestinationPrefixes("/app");
    }

    // WebSocket 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket") // 클라이언트가 연결할 WebSocket 엔드포인트
                .setAllowedOrigins("http://localhost:3000", "http://app-deploy0918.s3-website.ap-northeast-2.amazonaws.com") // CORS 설정
                .withSockJS(); // SockJS 폴백 지원
    }

    // JSON 형식 메시지를 처리할 메시지 컨버터 추가
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        // Jackson 기반의 메시지 컨버터를 등록하여 JSON 직렬화/역직렬화 처리
        messageConverters.add(new MappingJackson2MessageConverter());
        return true; // 추가한 컨버터로 JSON 변환 처리
    }
}
