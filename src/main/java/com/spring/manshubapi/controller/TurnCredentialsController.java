package com.spring.manshubapi.controller;

import com.spring.manshubapi.service.TurnCredentialsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class TurnCredentialsController {

    private final TurnCredentialsService turnCredentialsService;

    @Autowired
    public TurnCredentialsController(TurnCredentialsService turnCredentialsService) {
        this.turnCredentialsService = turnCredentialsService;
    }

    @GetMapping("/api/getTurnCredentials")
    public Map<String, String> getTurnCredentials(@RequestParam String username) {
        log.info("Param username: " + username);
        return turnCredentialsService.generateTurnCredentials(username);
    }
}