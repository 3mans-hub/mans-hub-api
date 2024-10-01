package com.spring.manshubapi.dto.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class EmailVerificationResponseDto {

    private String email;
    private String verificationCode;
}
