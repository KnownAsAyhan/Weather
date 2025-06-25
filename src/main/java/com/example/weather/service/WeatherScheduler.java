package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring will treat this as a bean
@RequiredArgsConstructor // auto-generates constructor for final fields
public class WeatherScheduler {

    private final CityRepository cityRepository;
    private final WeatherService weatherService;

    /**
     * This method will run every hour.
     * It fetches all cities from the DB and calls the WeatherService
     */
    @Scheduled(fixedRate = 3600000) // ⏰ 1 hour = 3600000 milliseconds
    public void fetchWeatherForAllCities() {
        List<City> cities = cityRepository.findAll();

        for (City city : cities) {
            weatherService.fetchAndSaveWeather(city); // already implemented
        }
    }
}
