package com.company.hasjob.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;
    @ManyToOne
    private Users fromUserId;
    @ManyToOne
    private Users toUserId;
    private LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Tashkent")));
    private boolean isActive = true;

}
