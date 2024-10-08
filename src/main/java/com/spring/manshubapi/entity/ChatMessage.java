package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"user", "team"})
@EqualsAndHashCode(of = "messageId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class ChatMessage {

    @Id
    @Column(name = "message_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String messageId;

    @Setter
    @Column(name = "message_content")
    private String content;

    @Setter
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @JsonBackReference("message-user")
    @Setter
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
//    @JsonBackReference("message-team")
    @JsonIgnore
    private Team team;

    @PrePersist
    public void prePersist() {
        this.createAt = this.createAt == null ? LocalDateTime.now() : this.createAt;
    }
}
