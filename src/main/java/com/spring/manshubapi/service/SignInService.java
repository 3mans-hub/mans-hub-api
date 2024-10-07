package com.spring.manshubapi.service;

import com.spring.manshubapi.auth.TokenProvider;
import com.spring.manshubapi.dto.request.SignInRequestDto;
import com.spring.manshubapi.dto.request.SignInStatus;
import com.spring.manshubapi.dto.response.SignInResponseDto;
import com.spring.manshubapi.entity.User;
import com.spring.manshubapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final UserRepository userRepository;

    // 패스워드 암호화 객체
    private final PasswordEncoder encoder;

    private final TokenProvider tokenProvider;


    public SignInRequestDto signIn(SignInResponseDto signInResponseDto) {

        User user = userRepository.findByEmail(signInResponseDto.getEmail());

        String userInputPassword = signInResponseDto.getPassword();

        if(user == null || user.getPassword() == null) {

            return SignInRequestDto.builder()
                    .signInStatus(SignInStatus.EMAIL)
                    .isLogin(false)
                    .build();
        } else if (!encoder.matches(userInputPassword, user.getPassword())) {

            return SignInRequestDto.builder()
                    .signInStatus(SignInStatus.PASSWORD)
                    .isLogin(false)
                    .build();
        } else {

            // 여기서 토큰 발급하기
            String token = tokenProvider.createToken(user);


            return SignInRequestDto.builder()
                    .signInStatus(SignInStatus.SUCCESS)
                    .isLogin(true)
                    .email(user.getEmail())
                    .userId(user.getUserId())
                    .nickname(user.getName())
                    .token(token)
                    .build();
        }

    }
}
