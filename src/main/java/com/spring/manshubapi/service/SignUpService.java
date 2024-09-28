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


    public void registerEmail(EmailResponseDto emailResponseDto) {

        User responseUser = userRepository.findByEmail(emailResponseDto.getEmail());

        if (responseUser == null) {
            User newUser = User.builder()
                    .email(emailResponseDto.getEmail())
                    .build();

            userRepository.save(newUser);
        }

    }
}
