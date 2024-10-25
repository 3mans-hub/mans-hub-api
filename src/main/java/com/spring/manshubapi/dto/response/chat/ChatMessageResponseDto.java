package com.spring.manshubapi.dto.response.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
    private String name;  // 사용자 이름만 포함
    private String content;
    private String createAt;
}
