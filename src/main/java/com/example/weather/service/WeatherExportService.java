package com.example.weather.service;

import com.example.weather.entity.Weather;
import com.example.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherExportService {

    private final WeatherRepository weatherRepository;

    public InputStream exportLast24Hours(Long cityId) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusHours(24);

        List<Weather> weatherList = weatherRepository.findByCityIdAndTimestampBetween(cityId, yesterday, now);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Weather Data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Temperature");
        header.createCell(1).setCellValue("Humidity");
        header.createCell(2).setCellValue("Description");
        header.createCell(3).setCellValue("Timestamp");

        int rowNum = 1;
        for (Weather w : weatherList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(w.getTemperature());
            row.createCell(1).setCellValue(w.getHumidity());
            row.createCell(2).setCellValue(w.getDescription());
            row.createCell(3).setCellValue(w.getTimestamp().toString());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
