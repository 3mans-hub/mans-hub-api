package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString(exclude = {"group", "user"})
@EqualsAndHashCode(of = "boardId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "board_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String boardId;

    @Column(name = "board_name")
    private String name;

    @Column(name = "board_content")
    private String content;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "withdrawal")
    private boolean withdrawal;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference("board-team")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("board-user")
    private User user;
}
