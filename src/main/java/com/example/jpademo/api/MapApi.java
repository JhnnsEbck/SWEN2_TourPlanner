package com.example.jpademo.api;

import com.example.jpademo.persistence.entities.TourEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "address")
public interface MapApi {


    @GetMapping("/search/{text}")
    String searchAddress(@PathVariable String text);

    TourEntity searchTourInformation(TourEntity tourEntity);

    List<double[]> searchDirection(String start, String end);

}
