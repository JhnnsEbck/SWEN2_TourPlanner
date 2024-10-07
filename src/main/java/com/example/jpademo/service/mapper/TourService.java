package com.example.jpademo.service.mapper;

import com.example.jpademo.service.dtos.TourDto;
import com.example.jpademo.service.dtos.TourLogDto;

import java.util.List;

public interface TourService {


    List<TourDto> findAllTours();

    TourDto findTourById(Long id);

    TourDto saveTour(TourDto tourDto);

    TourDto updateTour(Long id, TourDto tourDto);

    void deleteTour(Long id);

    void updateTourAttributes(List<TourLogDto> logs);

}
