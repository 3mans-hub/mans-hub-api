package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = {"members"})
@EqualsAndHashCode(of = "groupId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "group")
public class Group {

    @Id
    @Column(name = "group_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String groupId;

    @Column(name = "group_name")
    private String name;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "withdrawal")
    private boolean withdrawal;

    @Column(name = "join_code")
    private String joinCode;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("group-members")
    private List<GroupMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("group-boards")
    private List<Board> boards = new ArrayList<>();;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("group-user")
    private User user;
}
