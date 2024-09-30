package com.spring.manshubapi.controller;

import com.spring.manshubapi.service.TurnCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TurnCredentialsController {

    @Autowired
    private TurnCredentialsService turnCredentialsService;

    @GetMapping("/getTurnCredentials")
    public Map<String, String> getTurnCredentials() {
        return turnCredentialsService.generateTurnCredentials("user");
    }
}