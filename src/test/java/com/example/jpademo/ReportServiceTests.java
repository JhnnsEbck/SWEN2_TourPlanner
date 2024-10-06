package com.example.jpademo;

import com.example.jpademo.service.dtos.TourLogDto;
import com.example.jpademo.service.impl.ReportService;
import com.example.jpademo.service.impl.TourLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReportServiceTests {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private TourLogService tourLogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateAverageTime() {
        long tourId = 1L;
        List<TourLogDto> logs = new ArrayList<>();
        TourLogDto logDto1 = new TourLogDto();
        TourLogDto logDto2 = new TourLogDto();

        logDto1.setTotalTime(Duration.ofMinutes(30));
        logDto2.setTotalTime(Duration.ofMinutes(45));

        logs.add(logDto1);
        logs.add(logDto2);

        when(tourLogService.findAllLogsByTour(tourId)).thenReturn(logs);

        Duration result = ReflectionTestUtils.invokeMethod(reportService, "calculateAverageTime", tourId);

        assertEquals(Duration.ofMinutes(37), result);
    }

    @Test
    public void testCalculateAverageDistance() {
        long tourId = 1L;
        List<TourLogDto> logs = new ArrayList<>();
        TourLogDto logDto1 = new TourLogDto();
        TourLogDto logDto2 = new TourLogDto();

        logDto1.setTotalDistance(10.0);
        logDto2.setTotalDistance(20.0);

        logs.add(logDto1);
        logs.add(logDto2);

        when(tourLogService.findAllLogsByTour(tourId)).thenReturn(logs);

        double result = ReflectionTestUtils.invokeMethod(reportService, "calculateAverageDistance", tourId);

        assertEquals(15.0, result);
    }

    @Test
    public void testCalculateAverageRating() {
        long tourId = 1L;
        List<TourLogDto> logs = new ArrayList<>();
        TourLogDto logDto1 = new TourLogDto();
        TourLogDto logDto2 = new TourLogDto();

        logDto1.setRating(3);
        logDto2.setRating(4);

        logs.add(logDto1);
        logs.add(logDto2);

        when(tourLogService.findAllLogsByTour(tourId)).thenReturn(logs);

        int result = ReflectionTestUtils.invokeMethod(reportService, "calculateAverageRating", tourId);

        assertEquals(3, result);
    }
}
