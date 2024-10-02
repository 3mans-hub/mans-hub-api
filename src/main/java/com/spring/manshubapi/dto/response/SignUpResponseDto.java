package com.spring.manshubapi.dto.response;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponseDto {

    public String email;
    public String password;
    public String nickName;

}
