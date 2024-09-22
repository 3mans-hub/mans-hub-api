package com.spring.manshubapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 나머지 경로에 대한 접근 허용
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint()); // 미인증 사용자가 접근할 경우 403 응답


        return null;
    }
}