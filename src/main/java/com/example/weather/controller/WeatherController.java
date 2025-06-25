package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.repository.CityRepository;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.WeatherExportService;
import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final CityRepository cityRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherExportService weatherExportService;


    // Existing endpoint: fetch weather from API for one city
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchWeather(@RequestParam Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));

        weatherService.fetchAndSaveWeather(city);
        return ResponseEntity.ok("Weather fetched and saved for city: " + city.getName());
    }

    // ✅ New endpoint: return all weather data from DB
    @GetMapping
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportWeather(@RequestParam Long cityId) throws Exception {
        InputStream is = weatherExportService.exportLast24Hours(cityId);

        byte[] bytes = is.readAllBytes();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=weather.xlsx")
                .body(bytes);
    }



}
