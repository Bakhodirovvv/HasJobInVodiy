package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdsSaveDto {
    private Integer userId;
    private String address;
    private String latitude;
    private String longitude;
    private String description;
    private Double price;
    private String title;
}
