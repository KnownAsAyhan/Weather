package com.example.weather;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling // 💡 This enables @Scheduled support
@SpringBootApplication
public class WeatherApplication {
	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}
}

