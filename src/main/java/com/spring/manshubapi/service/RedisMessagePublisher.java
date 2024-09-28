package com.spring.manshubapi.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;  // Redis에 메시지를 발행할 수 있는 RedisTemplate
    private final ChannelTopic topic;  // 메시지를 발행할 채널 토픽

    // 생성자 주입을 통해 RedisTemplate과 ChannelTopic 설정
    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    // 메시지를 Redis Pub/Sub 채널로 발행하는 메서드
    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);  // 지정된 채널에 메시지 발행
    }
}