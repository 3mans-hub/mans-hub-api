package com.spring.manshubapi.auth;

import com.spring.manshubapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
// 토큰을 생성하여 발급하고, 서명 위조를 검사하는 객체
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * JWT를 생성하는 메서드
     * @param user - 토큰에 포함될 로그인한 유저의 정보
     * @return - 생성된 JWT의 암호화된 문자열
     */
    public String createToken(User user) {


        // 토큰에 들어갈 커스텀 데이터 (추가 클레임)
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("nickname", user.getName());

        return Jwts.builder()
                // token에 들어갈 서명
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.HS512
                )
                // payload에 들어갈 클레임 설정
                .setClaims(claims) // 추가 클레임은 항상 가장 먼저 설정
                .setIssuer("3MansHub") // 발급자 정보
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(Date.from(
                        Instant.now().plus(1, ChronoUnit.DAYS)
                )) // 토큰 만료 시간
                .setSubject(user.getUserId()) // 토큰을 식별할수 있는 유일한 값
                .compact();
    }

    /**
     * 클라이언트가 전송한 토큰을 디코딩하여 토큰의 서명 위조 여부를 확인
     * 그리고 토큰을 JSON 으로 파싱하여 안에 들어있는 클레임(토큰 정보)을 리턴
     *
     * @param token - 클라이언트가 보낸 토큰
     * @return - 토큰에 들어있는 인증 정보들을 리턴 - 회원 식별 ID, 이메일, 닉네임
     */

    public TokenUserInfo validateAndGetTokenInfo(String token) {

        Claims claims = Jwts.parserBuilder()
                // 토큰 발급자의 발급 당시 서명을 넣음
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                // 서명위조 검사 진행 : 위조된 경우 Exception이 발생
                // 위조되지 않은 경우 클레임을 리턴
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("claims: {}", claims);

        // 토큰에 인증된 회원의 PK
        return TokenUserInfo.builder()
                .userId(claims.getSubject())
                .email(claims.get("email", String.class))
                .name(claims.get("nickname", String.class))
                .build();
    }


    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TokenUserInfo {
        private String userId;
        private String email;
        private String name;
    }

}






