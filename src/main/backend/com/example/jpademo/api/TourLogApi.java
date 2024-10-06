package com.example.jpademo.api;
import com.example.jpademo.service.dtos.TourLogDto;
import com.example.jpademo.service.impl.TourLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/tour-logs")
    public class TourLogApi {

        private final TourLogService tourLogService;

        @Autowired
        public TourLogApi(TourLogService tourLogService) {
            this.tourLogService = tourLogService;
        }


        @GetMapping("/{id}")
        public ResponseEntity<List<TourLogDto>> getLogsByTour(@PathVariable Long id) {
            return ResponseEntity.ok(tourLogService.findAllLogsByTour(id));
        }

        @PostMapping
        public ResponseEntity<TourLogDto> createTourLog(@RequestBody TourLogDto tourLogDto) {
            return ResponseEntity.ok(tourLogService.createTourLog(tourLogDto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<TourLogDto> updateTourLog(@PathVariable Long id, @RequestBody TourLogDto tourLogDto) {
            return ResponseEntity.ok(tourLogService.updateTourLog(id, tourLogDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTourLog(@PathVariable Long id) {
            tourLogService.deleteTourLog(id);
            return ResponseEntity.ok().build();
        }
}
