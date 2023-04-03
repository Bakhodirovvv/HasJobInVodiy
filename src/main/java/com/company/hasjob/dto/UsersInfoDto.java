package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersInfoDto {
    private String fio;
    private String job;
    private String photoUrl;
    private Integer experience;
}