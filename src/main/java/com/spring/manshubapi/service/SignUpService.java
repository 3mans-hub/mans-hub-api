package com.spring.manshubapi.service;

import com.spring.manshubapi.dto.response.EmailResponseDto;
import com.spring.manshubapi.entity.User;
import com.spring.manshubapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;


    /**
     *
     * @param emailResponseDto - 유저가 입력한 이메일
     * @return
     * email 을 통하여 유저 조회
     * DB에 저장되어있지 않은 유저라면 newUser 반환
     * DB에 저장되어있지만, password 가 등록되지 않은 유저라면 registering(등록중) 반환
     * DB에 저장되어있으며, password 도 등록되어있다면 registered(등록된) 반환
     */
    public String registerEmail(EmailResponseDto emailResponseDto) {

        try {
            User responseUser = userRepository.findByEmail(emailResponseDto.getEmail());

            if (responseUser == null) {
                User newUser = User.builder()
                        .email(emailResponseDto.getEmail())
                        .build();

                userRepository.save(newUser);

                return "newUser";
            } else if (responseUser.getPassword() == null) {
                return "registering";
            }

            return "registered";

        } catch (Exception e) {
            return null;
        }

    }
}
