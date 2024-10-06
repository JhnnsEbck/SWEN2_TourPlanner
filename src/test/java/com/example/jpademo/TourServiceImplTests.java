package com.example.jpademo;

import com.example.jpademo.persistence.entities.TourEntity;
import com.example.jpademo.persistence.repositories.TourRepository;
import com.example.jpademo.service.dtos.TourDto;
import com.example.jpademo.service.impl.MapApiImpl;
import com.example.jpademo.service.impl.TourServiceImpl;
import com.example.jpademo.service.mapper.TourMapper;
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

public class TourServiceImplTests {

    @InjectMocks
    private TourServiceImpl tourService;

    @Mock
    private TourRepository tourRepository;

    @Mock
    private TourMapper tourMapper;

    @Mock
    private MapApiImpl mapApiImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTour() {
        TourDto tourDto = new TourDto();
        TourEntity tourEntity = new TourEntity();
        TourEntity savedEntity = new TourEntity();

        when(tourMapper.mapToEntity(tourDto)).thenReturn(tourEntity);
        when(mapApiImpl.searchTourInformation(tourEntity)).thenReturn(tourEntity);
        when(tourRepository.save(tourEntity)).thenReturn(savedEntity);
        when(tourMapper.mapToDto(savedEntity)).thenReturn(tourDto);

        TourDto result = tourService.saveTour(tourDto);

        assertNotNull(result);
        verify(tourRepository, times(1)).save(tourEntity);
    }

    @Test
    public void testFindAllTours() {
        TourEntity tourEntity = new TourEntity();
        TourDto tourDto = new TourDto();

        when(tourRepository.findAll()).thenReturn(Collections.singletonList(tourEntity));
        when(tourMapper.mapToDto(tourEntity)).thenReturn(tourDto);

        List<TourDto> result = tourService.findAllTours();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tourRepository, times(1)).findAll();
    }

    @Test
    public void testFindTourById() {
        Long id = 1L;
        TourEntity tourEntity = new TourEntity();
        TourDto tourDto = new TourDto();

        when(tourRepository.findById(id)).thenReturn(Optional.of(tourEntity));
        when(tourMapper.mapToDto(tourEntity)).thenReturn(tourDto);

        TourDto result = tourService.findTourById(id);

        assertNotNull(result);
        verify(tourRepository, times(1)).findById(id);
    }

    @Test
    public void testFindTourById_NotFound() {
        Long id = 1L;

        when(tourRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tourService.findTourById(id));
        verify(tourRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateTour() {
        Long id = 1L;
        TourDto tourDto = new TourDto();
        TourEntity tourEntity = new TourEntity();
        TourEntity updatedEntity = new TourEntity();

        when(tourRepository.existsById(id)).thenReturn(true);
        when(tourMapper.mapToEntity(tourDto)).thenReturn(tourEntity);
        when(tourRepository.save(tourEntity)).thenReturn(updatedEntity);
        when(tourMapper.mapToDto(updatedEntity)).thenReturn(tourDto);

        TourDto result = tourService.updateTour(id, tourDto);

        assertNotNull(result);
        verify(tourRepository, times(1)).save(tourEntity);
    }

    @Test
    public void testUpdateTour_NotFound() {
        Long id = 1L;
        TourDto tourDto = new TourDto();

        when(tourRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> tourService.updateTour(id, tourDto));
        verify(tourRepository, times(1)).existsById(id);
    }

    @Test
    public void testDeleteTour() {
        Long id = 1L;

        doNothing().when(tourRepository).deleteById(id);

        tourService.deleteTour(id);

        verify(tourRepository, times(1)).deleteById(id);
    }
}
