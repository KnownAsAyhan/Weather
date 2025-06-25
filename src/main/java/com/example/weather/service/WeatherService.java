package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String API_KEY = "b81b487ecae2888dfa10a8c8cea372dc";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public void fetchAndSaveWeather(City city) {
        String url = BASE_URL + "?q=" + city.getName() + "," + city.getCountry()
                + "&appid=" + API_KEY + "&units=metric";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> body = response.getBody();

            Map<String, Object> main = (Map<String, Object>) body.get("main");
            String description = (String) ((Map<String, Object>) ((java.util.List<?>) body.get("weather")).get(0)).get("description");

            Weather weather = Weather.builder()
                    .city(city)
                    .temperature(Double.valueOf(main.get("temp").toString()))
                    .humidity(Integer.valueOf(main.get("humidity").toString()))
                    .description(description)
                    .timestamp(LocalDateTime.now())
                    .build();

            weatherRepository.save(weather);
        }
    }
}
