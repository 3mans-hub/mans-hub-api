package com.spring.manshubapi.repository.chat;

import com.spring.manshubapi.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByTeam_TeamId(String teamId);
}
