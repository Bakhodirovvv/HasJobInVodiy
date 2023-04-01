package com.company.hasjob.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    private JobType job;
    private String photoUrl;
    private Status status;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AuthRole authRole=AuthRole.USER;
    private LocalDateTime createdAt=LocalDateTime.now(Clock.system(ZoneId.of("Asia/Tashkent")));

    private enum Status {
        ACTIVE(0), NON_ACTIVE(-1), BLOCKED(-50), BANNED(-100);
        private final int level;

        Status(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    private enum AuthRole {
        ADMIN,USER
    }
}
