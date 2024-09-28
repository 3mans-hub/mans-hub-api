package com.spring.manshubapi.controller;


import com.spring.manshubapi.dto.response.EmailResponseDto;
import com.spring.manshubapi.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sign_up")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/email")
    public ResponseEntity<?> registerEmail (@RequestBody EmailResponseDto emailResponseDto) {

        signUpService.registerEmail(emailResponseDto);

        return ResponseEntity.ok().build();
    };

}
