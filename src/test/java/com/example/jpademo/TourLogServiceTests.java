package com.example.jpademo;

import com.example.jpademo.persistence.entities.TourEntity;
import com.example.jpademo.persistence.entities.TourLogEntity;
import com.example.jpademo.persistence.repositories.TourLogRepository;
import com.example.jpademo.persistence.repositories.TourRepository;
import com.example.jpademo.service.dtos.TourLogDto;
import com.example.jpademo.service.impl.TourLogService;
import com.example.jpademo.service.mapper.TourLogMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourLogServiceTests {

    @InjectMocks
    private TourLogService tourLogService;

    @Mock
    private TourLogRepository tourLogRepository;

    @Mock
    private TourLogMapper tourLogMapper;

    @Mock
    private TourRepository tourRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllLogsByTour() {
        Long tourId = 1L;
        TourEntity tourEntity = new TourEntity();
        tourEntity.setId(tourId);
        TourLogEntity tourLogEntity = new TourLogEntity();
        TourLogDto tourLogDto = new TourLogDto();

        when(tourRepository.findById(tourId)).thenReturn(Optional.of(tourEntity));
        when(tourLogRepository.findByTourId(tourId)).thenReturn(Collections.singletonList(tourLogEntity));
        when(tourLogMapper.mapToDto(tourLogEntity)).thenReturn(tourLogDto);

        List<TourLogDto> result = tourLogService.findAllLogsByTour(tourId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tourRepository, times(1)).findById(tourId);
        verify(tourLogRepository, times(1)).findByTourId(tourId);
    }

    @Test
    public void testFindAllLogsByTour_TourNotFound() {
        Long tourId = 1L;

        when(tourRepository.findById(tourId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourLogService.findAllLogsByTour(tourId));
        verify(tourRepository, times(1)).findById(tourId);
    }

    @Test
    public void testDeleteTourLog() {
        Long id = 1L;

        doNothing().when(tourLogRepository).deleteById(id);

        tourLogService.deleteTourLog(id);

        verify(tourLogRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTourLog() {
        Long id = 1L;
        TourLogDto tourLogDto = new TourLogDto();
        TourLogEntity tourLogEntity = new TourLogEntity();
        TourLogEntity updatedLog = new TourLogEntity();

        when(tourLogRepository.existsById(id)).thenReturn(true);
        when(tourLogMapper.mapToEntity(tourLogDto)).thenReturn(tourLogEntity);
        when(tourLogRepository.save(tourLogEntity)).thenReturn(updatedLog);
        when(tourLogMapper.mapToDto(updatedLog)).thenReturn(tourLogDto);

        TourLogDto result = tourLogService.updateTourLog(id, tourLogDto);

        assertNotNull(result);
        verify(tourLogRepository, times(1)).existsById(id);
        verify(tourLogRepository, times(1)).save(tourLogEntity);
    }

    @Test
    public void testUpdateTourLog_NotFound() {
        Long id = 1L;
        TourLogDto tourLogDto = new TourLogDto();

        when(tourLogRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> tourLogService.updateTourLog(id, tourLogDto));
        verify(tourLogRepository, times(1)).existsById(id);
    }
}