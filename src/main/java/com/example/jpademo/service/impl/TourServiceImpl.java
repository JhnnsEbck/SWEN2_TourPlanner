package com.example.jpademo.service.impl;

import com.example.jpademo.persistence.entities.TourEntity;
import com.example.jpademo.persistence.repositories.TourRepository;
import com.example.jpademo.service.dtos.TourDto;
import com.example.jpademo.service.dtos.TourLogDto;
import com.example.jpademo.service.mapper.TourMapper;
import com.example.jpademo.service.mapper.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final MapApiImpl mapApiImpl;

    public TourServiceImpl(TourRepository tourRepository, TourMapper tourMapper, MapApiImpl mapApiImpl) {
        this.tourRepository = tourRepository;
        this.tourMapper = tourMapper;
        this.mapApiImpl = mapApiImpl;
    }

    public TourDto saveTour(TourDto tourDto) {
        // Map DTO to Entity
        TourEntity tourEntity = tourMapper.mapToEntity(tourDto);

        //search tour information via OpenMapAPI
        tourEntity = mapApiImpl.searchTourInformation(tourEntity);

        // Save Entity
        TourEntity savedEntity = tourRepository.save(tourEntity);

        log.info("createdTour: {}", savedEntity);

        // Map saved Entity back to DTO
        return tourMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourDto> findAllTours() {
        return tourRepository.findAll().stream()
                .map(tourMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TourDto findTourById(Long id) {
        return tourRepository.findById(id)
                .map(tourMapper::mapToDto)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
    }


    @Override
    @Transactional
    public TourDto updateTour(Long id, TourDto tourDto) {
        if (!tourRepository.existsById(id)) {
            throw new RuntimeException("Tour not found with id: " + id);
        }
        TourEntity tourEntity = tourMapper.mapToEntity(tourDto);
        tourEntity.setId(id);
        TourEntity updatedEntity = tourRepository.save(tourEntity);
        log.info("updatedTour: #{} to {}", id, updatedEntity);
        return tourMapper.mapToDto(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteTour(Long id) {
        tourRepository.deleteById(id);
        log.info("deletedTour: {}", id);
    }

    @Override
    @Transactional
    public void updateTourAttributes(List<TourLogDto> logs){
        int popularity = this.calculatePopularity(logs);
        int childFriendliness = this.calculateChildFriendliness(logs);

        TourDto tourDto = this.findTourById(logs.get(0).getTourId());
        tourDto.setPopularity(popularity);
        tourDto.setChildFriendliness(childFriendliness);
        updateTour(logs.get(0).getTourId(), tourDto);
    }

    private int calculatePopularity(List<TourLogDto> logs) {
        int popularity = logs.size()/2;
        if(popularity > 4){
            popularity = 4;
        }
        return popularity;
    }

    private int calculateChildFriendliness(List<TourLogDto> logs) {
        int childFriendliness;
        int medianDifficulty = calculateMedianDifficulty(logs);
        Duration medianDuration = calculateMedianDuration(logs);

        if(medianDuration.compareTo(Duration.ofMinutes(150)) > 0){
            childFriendliness = 1;
        }
        else if(medianDuration.compareTo(Duration.ofMinutes(120)) > 0){
            childFriendliness = 2;
        }
        else if(medianDuration.compareTo(Duration.ofMinutes(90)) > 0){
            childFriendliness = 3;
        }
        else{
            childFriendliness = 4;
        }

        if(medianDifficulty > 4){
            medianDifficulty = 4;
        }
        return (childFriendliness + medianDifficulty) / 2;
    }

    private int calculateMedianDifficulty(List<TourLogDto> logs) {
        List<Integer> difficulties = new ArrayList<>();
        for(TourLogDto log : logs){
            difficulties.add(log.getDifficulty());
        }
        Collections.sort(difficulties);

        if(difficulties.size() % 2 == 1){
                return difficulties.get(difficulties.size()/2);
        }
        else{
            return difficulties.get(difficulties.size()/2 - 1) + difficulties.get(difficulties.size()/2) /2;
        }
    }

    private Duration calculateMedianDuration(List<TourLogDto> logs) {
        List<Duration> durations = new ArrayList<>();
        for(TourLogDto log : logs){
            durations.add(log.getTotalTime());
        }
        Collections.sort(durations);
        return durations.get(durations.size()/2);
    }
}
