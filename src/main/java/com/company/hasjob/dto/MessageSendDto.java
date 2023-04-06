package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSendDto {
    private String text;
    private Integer chatId;
    private String userPhoneNumber;
    private LocalDateTime localDateTime;
}
