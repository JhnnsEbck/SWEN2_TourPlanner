package com.example.jpademo.api;

import com.example.jpademo.service.dtos.TourDto;
import com.example.jpademo.service.dtos.TourLogDto;
import com.example.jpademo.service.impl.ReportService;
import com.example.jpademo.service.impl.TourLogService;
import com.example.jpademo.service.impl.TourServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(path = "report")
public class ReportApi {

    @Autowired
    private ReportService reportService;
    @Autowired
    private TourServiceImpl tourServiceImpl;
    @Autowired
    private TourLogService tourLogService;


    @GetMapping("/tour/{tourId}")
    public ResponseEntity<Resource> getTest(@PathVariable Long tourId, @RequestParam String target) throws IOException {
        TourDto tour = tourServiceImpl.findTourById(tourId);
        List<TourLogDto> logs = tourLogService.findAllLogsByTour(tourId);
        Path filePath = Paths.get(reportService.createTourReport(target, tour, logs));
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + target + "\"")
                .body(resource);
    }

    @GetMapping("/summary")
    public ResponseEntity<Resource> getSummary(@RequestParam String target) throws IOException {
        Path filePath = Paths.get(reportService.createSummaryReport(target));
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + target + "\"")
                .body(resource);
    }


}