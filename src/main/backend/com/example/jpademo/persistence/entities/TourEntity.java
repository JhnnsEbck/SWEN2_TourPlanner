package com.example.jpademo.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;


@Entity
@Table(name="tours")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "end_location", nullable = false)
    private String endLocation;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "tour_distance")
    private Double tourDistance;

    @Column(name = "estimated_time")
    private Duration estimatedTime;

    @Column(name = "route_image_path")
    private String routeImagePath;

    @Column(name = "popularity")
    private int popularity;

    @Column(name = "child_friendliness")
    private int childFriendliness;

}
