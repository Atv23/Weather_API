package com.weather.api.WeatherAPI.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="weather-client", url="https://api.openweathermap.org/data/")
public interface WeatherFeignClient {

    @GetMapping("3.0/onecall/timemachine")
    Map<String,Object> getWeather(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            @RequestParam("dt") long timestamp,
            @RequestParam("appid") String appid
    );
    @GetMapping("2.5/weather")
    Map<String,Object> getWeatherForZip(
            @RequestParam("zip") double pincode,
            @RequestParam("appid") String appid
    );
}
