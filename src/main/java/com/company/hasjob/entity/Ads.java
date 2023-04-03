package com.company.hasjob.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Users user;
    private String address;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String title;
    @Builder.Default
    private boolean isActive=true;


}
