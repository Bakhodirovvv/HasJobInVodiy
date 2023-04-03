package com.company.hasjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

//import java.time.Clock;
import java.time.LocalDateTime;
//import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String fio;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    @ManyToOne(optional = false)
    private JobType job;
    private String photoUrl;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AuthRole authRole = AuthRole.USER;
    private Integer experience;
    private int rate=5;
    //    @CreationTimestamp
//    private LocalDateTime createdAt = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Tashkent")));
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    private enum Status {
        ACTIVE, NON_ACTIVE, BLOCKED, BANNED;
    }

    private enum AuthRole {
        ADMIN, USER
    }
}
