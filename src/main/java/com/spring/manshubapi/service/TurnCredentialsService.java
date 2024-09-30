package com.spring.manshubapi.service;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class TurnCredentialsService {

    // TURN 서버에서 설정한 static-auth-secret 값
    private static final String TURN_SECRET = "32cab9e617dbc639e42ee7cee3cfe467";

    // TURN 자격 증명을 생성하는 메서드
    public Map<String, String> generateTurnCredentials(String username) {
        try {
            // 현재 시간에서 24시간 후의 Unix 타임스탬프 생성
            long unixTime = System.currentTimeMillis() / 1000L + 24 * 3600;
            String usernameWithTimestamp = unixTime + ":" + username;  // 타임스탬프와 사용자 이름 결합

            // HMAC-SHA1 알고리즘을 사용해 자격 증명 생성
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(TURN_SECRET.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            byte[] macBytes = mac.doFinal(usernameWithTimestamp.getBytes());

            // 자격 증명을 base64로 인코딩하여 credential로 사용
            String credential = Base64.getEncoder().encodeToString(macBytes);

            // 자격 증명을 담을 Map 객체 생성
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", usernameWithTimestamp);
            credentials.put("credential", credential);

            return credentials;  // 생성된 자격 증명을 반환

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate TURN credentials", e);  // 예외 발생 시 처리
        }
    }
}