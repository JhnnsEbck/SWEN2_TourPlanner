package com.example.jpademo.service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourDto {

    private Long id;
    private String name;
    private String description;
    private String startLocation;
    private String endLocation;
    private String transportType;
    private Double tourDistance;
    private Duration estimatedTime;
    private String routeImagePath;
    private int popularity;
    private int childFriendliness;
}
