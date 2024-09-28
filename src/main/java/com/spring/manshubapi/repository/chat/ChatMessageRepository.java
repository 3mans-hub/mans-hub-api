package com.spring.manshubapi.repository.chat;

import com.spring.manshubapi.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


}
