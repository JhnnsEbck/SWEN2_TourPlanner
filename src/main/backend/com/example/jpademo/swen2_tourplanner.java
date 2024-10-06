package com.example.jpademo;

import com.example.jpademo.persistence.entities.TourEntity;
import com.example.jpademo.persistence.repositories.TourRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;

@SpringBootApplication
public class swen2_tourplanner {

    @Autowired
    private TourRepository tourRepository;

    public static void main(String[] args) {
        SpringApplication.run(swen2_tourplanner.class, args);
    }

    @PostConstruct
    public void initTourData() {
        // Assuming you have repositories and builders set up for Tours and LogList
        TourEntity tour = TourEntity.builder()
                .name("FH-Tour")
                .description("Explore historic Vienna")
                .startLocation("Austria, Vienna, Höchstädtplatz 8")
                .endLocation("Austria, Vienna, Dresdner Straße 100")
                .transportType("foot-walking")
                .tourDistance(100.0)
                .estimatedTime(Duration.ofHours(3))
                .build();
        tourRepository.save(tour);

        System.out.println("Initialized tours and tour logs.");
    }

}
