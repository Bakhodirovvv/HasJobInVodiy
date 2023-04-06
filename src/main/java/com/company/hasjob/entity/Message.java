package com.company.hasjob.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String text;
    @ManyToOne
    private Chat chat;
    @ManyToOne
    private Users users;
    @Builder.Default
    private LocalDateTime localDateTime = LocalDateTime.now();
    @Builder.Default
    private boolean isActive = true;

}
