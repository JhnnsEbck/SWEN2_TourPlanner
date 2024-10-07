package com.example.jpademo.api;

import com.example.jpademo.service.dtos.TourDto;
import com.example.jpademo.service.mapper.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tour")
public class TourApi {

    private final TourService tourService;

    @Autowired
    public TourApi(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAllTours() {
        List<TourDto> tours = tourService.findAllTours();
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TourDto> getTourById(@PathVariable Long id) {
        TourDto tour = tourService.findTourById(id);
        return ResponseEntity.ok(tour);
    }

    @PostMapping
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        TourDto savedTour = tourService.saveTour(tourDto);
        return ResponseEntity.ok(savedTour);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<TourDto> updateTour(@PathVariable Long id, @RequestBody TourDto tourDto) {
        TourDto updatedTour = tourService.updateTour(id, tourDto);
        return ResponseEntity.ok(updatedTour);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.ok().build();
    }

}
