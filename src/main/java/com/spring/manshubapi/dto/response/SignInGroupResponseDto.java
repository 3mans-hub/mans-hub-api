package com.spring.manshubapi.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInGroupResponseDto {

    private String email;
    private String joinCode;

}
