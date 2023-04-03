package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseManagerDto {
    private String fio;
    private String phoneNumber;
    private String photoUrl;
    private String job;
    private int rate;
}
