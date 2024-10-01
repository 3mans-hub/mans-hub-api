package com.spring.manshubapi.repository;

import com.spring.manshubapi.entity.EmailVerification;
import com.spring.manshubapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {

    EmailVerification findByUser(User user);

}
