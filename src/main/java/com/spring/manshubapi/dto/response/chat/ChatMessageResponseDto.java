package com.spring.manshubapi.dto.response.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private String name;
    private String content;
}
