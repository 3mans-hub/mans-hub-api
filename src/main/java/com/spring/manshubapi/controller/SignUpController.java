package com.spring.manshubapi.controller;


import com.spring.manshubapi.dto.response.EmailResponseDto;
import com.spring.manshubapi.dto.response.EmailVerificationResponseDto;
import com.spring.manshubapi.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sign_up")
public class SignUpController {

    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);
    private final SignUpService signUpService;

    /**
     * 회원가입시 이메일을 입력받았을 때 이메일 중복검사 실행을 위한 함수
     * 이메일의 가입 상태에 따라 반환값이 달라짐 ( 새 유저, 가입중, 가입됨 )
     *
     * @param emailResponseDto - 유저가 입력한 이메일
     * @return
     * 예외발생시 badRequest 전송
     * 가입중인지, 새로운 유저인지, 가입이 되었는지 상태값을 전해줌.
     * 가입된 유저가 아니라면 인증메일 발송
     */
    @PostMapping("/email")
    public ResponseEntity<?> registerEmail (@RequestBody EmailResponseDto emailResponseDto) {

        String register = signUpService.registerEmail(emailResponseDto);

        if(register == null) {
            return ResponseEntity.badRequest().body(emailResponseDto);
        } else if (!register.equals("registered") ) {
            String verificationCode = signUpService.sendVerificationEmail(emailResponseDto.getEmail());

            log.info(verificationCode);
        }

        System.out.println("register = " + register);

        return ResponseEntity.ok().body(register);
    };

    /**
     * 인증번호 검증 메서드
     * @param emailVerificationDto - 가입하려는 이메일, 유저가 입력한 인증코드로 구성된 Dto
     * @return - 인증번호 만료시간 전에 요청한 처리이며, 인증번호가 일치하다면 200 전송
     * 아니라면 badRequest 전송.
     */
    @PostMapping("/check_verificationCode")
    public ResponseEntity<?> checkVerificationCode(@RequestBody EmailVerificationResponseDto emailVerificationDto) {

        String checked = signUpService.checkVerificationCode(emailVerificationDto);

        if(checked.equals("인증 성공")) {
            return ResponseEntity.ok().body(checked);
        } else {
            return ResponseEntity.badRequest().body(checked);
        }

    }

}
