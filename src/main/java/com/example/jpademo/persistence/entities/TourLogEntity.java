package com.example.jpademo.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tour_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false)
    private Double totalDistance;

    @Column(nullable = false)
    private Duration totalTime;

    @Column(nullable = false)
    private Integer rating;

    @Column(name = "fk_tour_id", nullable = false)
    private Long tourId;
 }

