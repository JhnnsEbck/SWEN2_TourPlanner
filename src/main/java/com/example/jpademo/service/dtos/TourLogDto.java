package com.example.jpademo.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class TourLogDto {
    private Long id;
    private LocalDateTime timestamp;
    private String comment;
    private Integer difficulty;
    private Double totalDistance;
    private Duration totalTime;
    private Integer rating;
    private Long tourId;
}
