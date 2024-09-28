package com.spring.manshubapi.controller;

import com.spring.manshubapi.dto.response.chat.ChatMessageInfo;
import com.spring.manshubapi.entity.ChatMessage;
import com.spring.manshubapi.repository.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;

    // 클라이언트가 "/app/sendMessage"로 메시지를 보낼 때 처리
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage broadcastMessage(@Payload ChatMessage message) {
        // 받은 메시지 로그로 출력
        System.out.println("Received message: " + message.getUser() + ", " + message.getContent());

        // 클라이언트에서 받은 메시지가 제대로 들어왔는지 확인
        if (message.getContent() == null || message.getUser() == null) {
            System.out.println("Error: Missing text or user in message");
        }

        // 메시지를 DB에 저장
        System.out.println("Received message: " + message);
        ChatMessage entity = ChatMessage.builder()
                .user(message.getUser())  // 사용자 설정
                .content(message.getContent())  // 메시지 내용 설정
                .build();
        chatMessageRepository.save(entity);

        // 메시지를 다른 구독자에게 브로드캐스트
        return entity;  // 저장한 엔티티 반환
    }

    // 채팅방에 입장할 때, 이전 채팅 기록을 불러오기
    @MessageMapping("/loadMessages")
    @SendTo("/topic/public")
    public List<ChatMessageInfo> loadPreviousMessages() {
        // DB에 저장된 이전 메시지들을 불러오기 (엔티티 -> DTO 변환)
        return chatMessageRepository.findAll()
                .stream()
                .map(entity -> new ChatMessageInfo(entity.getUser().getName(), entity.getContent()))
                .collect(Collectors.toList());
    }

}
