package com.weather.api.WeatherAPI.service;

import com.weather.api.WeatherAPI.entity.Weather;

import java.time.LocalDate;

public interface WeatherService {
    Weather getWeather(String pincode, long timestamp);

    Weather getWeatherByZip(String pincode);
}
