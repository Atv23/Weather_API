package com.weather.api.WeatherAPI.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="pincode-client", url="https://api.openweathermap.org/data/2.5")
public interface PincodeFeignClient {

    @GetMapping("/weather")
    Map<String,Object> getDetails(
            @RequestParam("zip") String zip,
            @RequestParam("appid") String appid
    );
}

