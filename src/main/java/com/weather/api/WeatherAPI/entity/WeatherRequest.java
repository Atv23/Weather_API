package com.weather.api.WeatherAPI.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WeatherRequest {
    private String pincode;
    private String for_date;
}
