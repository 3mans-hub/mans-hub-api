package com.spring.manshubapi.controller;

import com.spring.manshubapi.service.TurnCredentialsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class VoiceChannelController {

    private final TurnCredentialsService turnCredentialsService;


    @MessageMapping("/join-voice-channel")
    @SendTo("/topic/join-voice-channel")
    public Map<String, String> joinVoiceChannel(@RequestBody String groupName) {
        log.info("음성 채널에 대한 요청이 수신되었습니다 - 그룹 이름: {}", groupName);
        // TURN 서버 자격 증명 생성
        return turnCredentialsService.generateTurnCredentials(groupName);
    }
}