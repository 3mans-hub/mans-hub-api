package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = {"groupMembers", "notifications", "messages"})
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mans_users")
public class User {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String userId;

    @Column(name = "name")
    @Setter
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    @Setter
    private String password;

    @Column(name = "create_at")
    @Setter
    private LocalDateTime createAt;

    @Column(name = "withdrawal")
    private boolean withdrawal;

    // 연관 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("user-groupMembers")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("user-notifications")
    private List<Notice> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("user-messages")
    private List<ChatMessage> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("user-boards")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "requestUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("user-requestFriendships")
    private List<Friendship> requestFriendships = new ArrayList<>();

    @OneToMany(mappedBy = "responseUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("user-responseFriendships")
    private List<Friendship> responseFriendships = new ArrayList<>();

    // 테스트커밋
}
