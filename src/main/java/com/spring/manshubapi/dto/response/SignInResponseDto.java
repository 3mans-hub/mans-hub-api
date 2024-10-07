package com.spring.manshubapi.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponseDto {

    private String email;
    private String password;
    private boolean autoLogin;
}
