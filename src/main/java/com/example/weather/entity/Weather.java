package com.example.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperature;

    private Integer humidity;

    private String description;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
