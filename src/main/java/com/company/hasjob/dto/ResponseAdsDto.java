package com.company.hasjob.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAdsDto {
    private String title;
    private String address;
    private String description;
    private String userName;
    private String latitude;
    private String longitude;
    private Double price;
}
