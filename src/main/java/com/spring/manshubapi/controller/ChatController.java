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
        User userId = userRepository.findById(messageDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Team teamId = teamRepository.findById(messageDto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // ChatMessage 엔티티 생성 (createAt은 @PrePersist로 자동 설정)
        ChatMessage messageEntity = ChatMessage.builder()
                .content(messageDto.getContent())
                .user(userId)
                .team(teamId)
                .build();

        chatMessageRepository.save(messageEntity);

        return new ChatMessageResponseDto(userId.getName(), messageDto.getContent(), messageEntity.getCreateAt().toString());
    }


    // 채팅방에 입장할 때, 이전 채팅 기록을 불러오기
    @MessageMapping("/loadMessages")
    @SendTo("/topic/public")
    @Transactional
    public List<ChatMessageResponseDto> loadPreviousMessages(@Payload ChatMessageDto messageDto) {

        return chatMessageRepository.findByTeam_TeamId(messageDto.getTeamId())
                .stream()
                .map(entity -> new ChatMessageResponseDto(entity.getUser() != null ? entity.getUser().getName() : "Unknown User",
                        entity.getContent(), entity.getCreateAt().toString()
                ))
                .collect(Collectors.toList());

    }
}