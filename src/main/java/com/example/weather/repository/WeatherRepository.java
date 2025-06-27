package com.example.weather.repository;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findByCityIdAndTimestampAfter(Long cityId, LocalDateTime afterTime);
    List<Weather> findByCityIdAndTimestampBetween(Long cityId, LocalDateTime start, LocalDateTime end);
    List<Weather> findByCityAndTimestampBetweenOrderByTimestampDesc(City city, LocalDateTime from, LocalDateTime to);

}
