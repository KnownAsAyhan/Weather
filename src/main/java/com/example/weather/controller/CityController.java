package com.example.weather.controller;

import com.example.weather.dto.CityRequest;
import com.example.weather.entity.City;
import com.example.weather.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityRepository cityRepository;

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody CityRequest request) {
        City city = City.builder()
                .name(request.getName())
                .country(request.getCountry())
                .build();

        City saved = cityRepository.save(city);
        return ResponseEntity.ok(saved);
    }


}
