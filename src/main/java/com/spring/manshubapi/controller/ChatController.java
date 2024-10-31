package com.spring.manshubapi.controller;

import com.spring.manshubapi.dto.response.chat.ChatMessageDto;
import com.spring.manshubapi.dto.response.chat.ChatMessageResponseDto;
import com.spring.manshubapi.entity.ChatMessage;
import com.spring.manshubapi.entity.Team;
import com.spring.manshubapi.entity.User;
import com.spring.manshubapi.repository.TeamRepository;
import com.spring.manshubapi.repository.UserRepository;
import com.spring.manshubapi.repository.chat.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    // 클라이언트가 "/app/sendMessage"로 메시지를 보낼 때 처리


    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessageResponseDto broadcastMessage(@Payload ChatMessageDto messageDto) {
        // DateTimeFormatter 를 사용하여 시간 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        User userId = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team teamId = teamRepository.findById(messageDto.getTeamId()).orElseThrow(() -> new RuntimeException("Team not found"));

        // ChatMessage 엔티티 생성 및 저장
        ChatMessage messageEntity = ChatMessage.builder()
                .content(messageDto.getContent())
                .createAt(LocalDateTime.parse(messageDto.getCreateAt(), formatter))
                .user(userId)
                .team(teamId)
                .build();

        chatMessageRepository.save(messageEntity);

        // 응답 DTO 반환
        return new ChatMessageResponseDto(userId.getName(), messageDto.getContent(), messageDto.getCreateAt());
    }

    // 채팅방에 입장할 때, 이전 채팅 기록을 불러오기
    @MessageMapping("/loadMessages")
    @SendTo("/topic/public")
    @Transactional  // 트랜잭션 보장
    public List<ChatMessageResponseDto> loadPreviousMessages() {
        // DB에 저장된 이전 메시지들을 불러오기 (엔티티 -> DTO 변환)
        return chatMessageRepository.findAll()
                .stream()
                .map(entity -> new ChatMessageResponseDto(
                        entity.getUser() != null ? entity.getUser().getName() : "Unknown User",
                        entity.getContent(), entity.getCreateAt().toString()
                ))
                .collect(Collectors.toList());
    }

}
