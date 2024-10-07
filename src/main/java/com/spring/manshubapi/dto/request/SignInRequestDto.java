package com.spring.manshubapi.dto.request;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class SignInRequestDto {

    private SignInStatus signInStatus;
    private Boolean isLogin;
    private String userId;
    private String email;
    private String nickname;
    private String token;

}
