package com.example.jpademo.persistence.repositories;


import com.example.jpademo.persistence.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<TourEntity,Long> {

    List<TourEntity> findByStartLocation(String startLocation);

    // Find tours that match a part of the name (case-insensitive search)
    List<TourEntity> findByNameContainingIgnoreCase(String name);

    // Find tours by transport type
    List<TourEntity> findByTransportType(String transportType);

}
