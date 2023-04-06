package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneChatDto {
    private String fromPhoneNumber;
    private String toPhoneNumber;
}
