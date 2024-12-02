package com.weather.api.WeatherAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Pincode {
    @Id
    private String pincode;
    private double latitude;
    private double longitude;
}
