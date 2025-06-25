package com.example.weather.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {
    private String name;
    private String country;
}
