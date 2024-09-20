package com.spring.manshubapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = "noticeTypeId")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notice_type")
public class NoticeType {
    @Id
    @Column(name = "notice_type_id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String noticeTypeId;

    @Column(name = "type_name")
    @Enumerated(EnumType.STRING)
    private NoticeTypeName typeName;

    @OneToMany(mappedBy = "noticeType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference("noticeType-notices")
    private List<Notice> notices = new ArrayList<>();

}
