package com.spring.manshubapi.controller;

import com.spring.manshubapi.dto.request.SignInRequestDto;
import com.spring.manshubapi.dto.response.SignInResponseDto;
import com.spring.manshubapi.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign_in")
public class SignInController {

    private final SignInService signInService;


    @PostMapping
    public ResponseEntity<?> SignIn (@RequestBody SignInResponseDto signInResponseDto) {

        SignInRequestDto signInRequestDto = signInService.signIn(signInResponseDto);


        return ResponseEntity.ok().body(signInRequestDto);
    }
}
