package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"user", "group"})
@EqualsAndHashCode(of = "groupMemberId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "group_member")
public class GroupMember {

    @Id
    @Column(name = "group_member_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String groupMemberId;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "withdrawal")
    private boolean withdrawal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("groupMember-user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference("groupMember-group")
    private Group group;

}