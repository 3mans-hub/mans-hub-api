package com.spring.manshubapi.controller;

import com.spring.manshubapi.dto.response.EmailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/find_password")
public class FindPasswordController {

    @PostMapping
    public ResponseEntity<?> checkEmail(@RequestParam EmailResponseDto emailResponseDto) {

        return ResponseEntity.ok(emailResponseDto);
    }
}
