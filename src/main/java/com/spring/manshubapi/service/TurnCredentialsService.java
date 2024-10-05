package com.spring.manshubapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TurnCredentialsService {

    @Value("${turn.secret}")
    private String TURN_SECRET;

    public Map<String, String> generateTurnCredentials(String username) {
        try {
            long unixTime = System.currentTimeMillis() / 1000L + 24 * 3600;
            String usernameWithTimestamp = unixTime + ":" + username;

            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(TURN_SECRET.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            byte[] macBytes = mac.doFinal(usernameWithTimestamp.getBytes());

            String credential = Base64.getEncoder().encodeToString(macBytes);

            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", usernameWithTimestamp);
            credentials.put("credential", credential);

            log.info("TURN 자격 증명 생성 완료 - 사용자 이름: {}, 자격 증명: {}", usernameWithTimestamp, credential);

            return credentials;

        } catch (Exception e) {
            log.error("TURN 자격 증명 생성 실패 - 사용자 이름: {}", username, e);
            throw new RuntimeException("TURN 자격 증명 생성에 실패하였습니다.", e);
        }
    }
}