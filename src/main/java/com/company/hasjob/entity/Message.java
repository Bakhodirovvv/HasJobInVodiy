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
    private String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"));
    @ManyToOne
    private Chat chat;
    private boolean isActive = true;

}
