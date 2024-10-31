package com.spring.manshubapi.dto.response.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String userId;
    private String content;
    private String createAt;
    private String name;
    private String teamId;

}
