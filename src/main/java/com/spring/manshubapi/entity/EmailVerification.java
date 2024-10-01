package com.spring.manshubapi.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"groupMembers", "notifications", "messages"})
@EqualsAndHashCode(of = "emailVerificationId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "email_verifications")
public class EmailVerification {

    @Id
    @Column(name = "verification_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String emailVerificationId;

    @Column(name = "verification_code", nullable = false)
    @Setter
    private String verificationCode;

    @Column(nullable = false)
    @Setter
    private LocalDateTime expiryDate; // 인증

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
