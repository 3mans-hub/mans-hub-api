package com.spring.manshubapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfig {

    // Redis 연결을 생성하는 Factory bean 정의
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379); // 로컬 Redis 서버의 호스트와 포트 설정
    }

    // Redis에 접근할 수 있는 RedisTemplate 정의 (Redis 데이터를 읽고 쓰기 위한 설정)
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory()); // 위에서 설정한 연결 팩토리를 이용해 설정
        return template;
    }

    // Redis 메시지를 수신할 수 있는 RedisMessageListenerContainer 설정
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory); // Redis 연결 팩토리를 설정
        return container;
    }

    // Redis Pub/Sub 채널 주제 설정
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("webrtc_signaling");  // "webrtc_signaling"이라는 이름의 채널
    }
}
