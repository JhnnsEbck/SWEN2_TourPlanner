package com.example.jpademo.service.mapper;

import com.example.jpademo.persistence.entities.TourEntity;
import com.example.jpademo.service.dtos.TourDto;
import org.springframework.stereotype.Component;


@Component
public class TourMapper extends AbstractMapper<TourEntity, TourDto> {

    @Override
    public TourDto mapToDto(TourEntity source) {
        if (source == null) {
            return null;
        }
        return TourDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .startLocation(source.getStartLocation())
                .endLocation(source.getEndLocation())
                .transportType(source.getTransportType())
                .tourDistance(source.getTourDistance())
                .estimatedTime(source.getEstimatedTime())
                .routeImagePath(source.getRouteImagePath())
                .popularity(source.getPopularity())
                .childFriendliness(source.getChildFriendliness())
                .build();
    }

    public TourEntity mapToEntity(TourDto source) {
        if (source == null) {
            return null;
        }
        TourEntity entity = new TourEntity();
        entity.setId(source.getId());
        entity.setName(source.getName());
        entity.setDescription(source.getDescription());
        entity.setStartLocation(source.getStartLocation());
        entity.setEndLocation(source.getEndLocation());
        entity.setTransportType(source.getTransportType());
        entity.setTourDistance(source.getTourDistance());
        entity.setEstimatedTime(source.getEstimatedTime());
        entity.setRouteImagePath(source.getRouteImagePath());
        entity.setPopularity(source.getPopularity());
        entity.setChildFriendliness(source.getChildFriendliness());
        return entity;
    }
}
