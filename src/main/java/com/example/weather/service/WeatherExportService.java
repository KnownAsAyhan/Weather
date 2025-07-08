package com.example.weather.service;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.example.weather.repository.CityRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherExportService {

    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;

    public InputStream exportLast24Hours(Long cityId) throws IOException {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusHours(24);

        List<Weather> weatherList = weatherRepository
                .findByCityAndTimestampBetweenOrderByTimestampDesc(city, yesterday, now);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Weather");

        // 🔹 Header row 🔹
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("City");
        header.createCell(1).setCellValue("Temperature");
        header.createCell(2).setCellValue("Humidity");
        header.createCell(3).setCellValue("Description");
        header.createCell(4).setCellValue("Timestamp");

        // 🔹 Format for timestamp 🔹
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        // 🔹 Data rows 🔹
        int rowNum = 1;
        for (Weather w : weatherList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(city.getName()); // city name
            row.createCell(1).setCellValue(w.getTemperature());
            row.createCell(2).setCellValue(w.getHumidity());
            row.createCell(3).setCellValue(w.getDescription());
            row.createCell(4).setCellValue(w.getTimestamp().format(formatter)); // formatted timestamp
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

}
