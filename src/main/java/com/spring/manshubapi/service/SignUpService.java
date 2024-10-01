package com.spring.manshubapi.service;

import com.spring.manshubapi.dto.response.EmailResponseDto;
import com.spring.manshubapi.entity.User;
import com.spring.manshubapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    // 이메일 전송 객체
    private final JavaMailSender mailSender;

    @Value("${study.mail.host}")
    private String host;


    /**
     *
     * @param emailResponseDto - 유저가 입력한 이메일
     * @return
     * email 을 통하여 유저 조회
     * DB에 저장되어있지 않은 유저라면 newUser 반환
     * DB에 저장되어있지만, password 가 등록되지 않은 유저라면 registering(등록중) 반환
     * DB에 저장되어있으며, password 도 등록되어있다면 registered(등록된) 반환
     */
    public String registerEmail(EmailResponseDto emailResponseDto) {

        try {
            User responseUser = userRepository.findByEmail(emailResponseDto.getEmail());

            if (responseUser == null) {
                User newUser = User.builder()
                        .email(emailResponseDto.getEmail())
                        .build();

                userRepository.save(newUser);

                return "newUser";
            } else if (responseUser.getPassword() == null) {
                return "registering";
            }

            return "registered";

        } catch (Exception e) {
            return null;
        }

    }


    // 이메일 인증 코드 보내기
    public String sendVerificationEmail(String email) {

        // 검증 코드 생성하기
        String code = generateVerificationCode();

        // 이메일을 전송할 객체 생성
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            // 누구에게 이메일을 보낼 것인지
            mimeMessageHelper.setTo(email);
            // 이메일 제목 설정
            mimeMessageHelper.setSubject("[인증메일] 3mansHub 가입 인증 메일입니다.");
            // 이메일 내용 설정
            mimeMessageHelper.setText(
                    "인증 코드: <b style=\"font-weight: 700; letter-spacing: 5px; font-size: 30px;\">" + code + "</b>", true);

            // 전송자의 이메일 주소
            mimeMessageHelper.setFrom(host);

            // 이메일 보내기
            mailSender.send(mimeMessage);

            return code;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 검증 코드 생성 로직 1000~9999 사이의 4자리 숫자
    private String generateVerificationCode() {
        return String.valueOf((int)(Math.random() * 9000 + 1000));
    }
}
