package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"user", "noticeType"})
@EqualsAndHashCode(of = "noticeId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @Column(name = "notice_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String noticeId;

    @Column(name = "notice_text")
    private String text;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "is_clicked")
    private boolean isClicked;

    @Column(name = "notice_user_id")
    private String noticeUserId;    // 알림 대상 아이디

    @ManyToOne
    @JoinColumn(name = "notice_type_id")
    @JsonBackReference("notice-noticeType")
    private NoticeType noticeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("notice-user")
    private User user;

}

