package com.spring.manshubapi.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = "user")
@EqualsAndHashCode(of = "friendshipId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @Column(name = "friendship", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String friendshipId;

    @Column(name = "requestAt")
    private LocalDateTime requestAt;

    @Column(name = "type_name")
    @Enumerated(EnumType.STRING)
    private FriendshipTypeName typeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_user_id")
    @JsonBackReference("friendship_requestUser")
    private User requestUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_user_id")
    @JsonBackReference("friendship_responseUser")
    private User responseUser;

}
