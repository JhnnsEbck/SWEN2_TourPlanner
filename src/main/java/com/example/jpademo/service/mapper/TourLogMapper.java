package com.example.jpademo.service.mapper;

import com.example.jpademo.persistence.entities.TourLogEntity;
import com.example.jpademo.service.dtos.TourLogDto;
import org.springframework.stereotype.Component;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLogDto> {

    @Override
    public TourLogDto mapToDto(TourLogEntity source) {
        if (source == null) {
            return null;
        }
        return TourLogDto.builder()
                .id(source.getId())
                .timestamp(source.getTimestamp())
                .comment(source.getComment())
                .difficulty(source.getDifficulty())
                .totalDistance(source.getTotalDistance())
                .totalTime(source.getTotalTime())
                .rating(source.getRating())
                .tourId(source.getTourId())
                .build();
    }

    public TourLogEntity mapToEntity(TourLogDto source) {
        if (source == null) {
            return null;
        }
        TourLogEntity entity = new TourLogEntity();
        entity.setId(source.getId());
        entity.setTimestamp(source.getTimestamp());
        entity.setComment(source.getComment());
        entity.setDifficulty(source.getDifficulty());
        entity.setTotalDistance(source.getTotalDistance());
        entity.setTotalTime(source.getTotalTime());
        entity.setRating(source.getRating());
        entity.setTourId(source.getTourId());
        return entity;
    }
}

/*
public class TourLogMapper {
// Method to convert TourLogEntity to TourLogDto
    public TourLogDto mapToTourLogDto(TourLogEntity source) {
        if (source == null) {
            return null;
        }
        return TourLogDto.builder()
                .id(source.getId())
                .details(source.getDetails())
                .timestamp(source.getTimestamp())
                .tourId(source.getTour().getId())  // Assuming the tour is not null
                .build();
    }

    // Method to convert TourLogDto to TourLogEntity
    public TourLogEntity mapToTourLogEntity(TourLogDto source, TourEntity tour) {
        if (source == null) {
            return null;
        }
        TourLogEntity entity = new TourLogEntity();
        entity.setId(source.getId());
        entity.setComment(source.getComment());
        entity.setTimestamp(source.getTimestamp());
        entity.setTour(tour);
        return entity;
    }
}

 */
