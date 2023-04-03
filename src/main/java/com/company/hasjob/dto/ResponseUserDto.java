package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseUserDto {
    private String fio;
    private String phoneNumber;
    private String jobName;
    private String photoUrl;
    private Integer experience;
    private Integer rate;
}
