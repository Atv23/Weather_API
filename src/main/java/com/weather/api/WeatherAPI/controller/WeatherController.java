package com.weather.api.WeatherAPI.controller;

import com.weather.api.WeatherAPI.entity.Weather;
import com.weather.api.WeatherAPI.entity.WeatherRequest;
import com.weather.api.WeatherAPI.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    private Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @PostMapping("/weather")
    public ResponseEntity<Weather> getWeatherInfo(@RequestBody WeatherRequest weatherRequest){
        String pincode = weatherRequest.getPincode();
        String forDate = weatherRequest.getFor_date();
        // Converting dorDate to Unix timestamp
        long timestamp = 0;
        try {
            LocalDate date = LocalDate.parse(forDate);
            timestamp = date.atStartOfDay(ZoneId.of("UTC")).toEpochSecond(); 
            Weather weather = weatherService.getWeather(pincode,timestamp);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Weather(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/weather-zip")
    public ResponseEntity<Weather> getWeatherInfoByZip(@RequestBody Map<String,String> request){
            String pincode = request.get("pincode");
            logger.info("Request received for pincode " + pincode);
            Weather weather = weatherService.getWeatherByZip(pincode);
            return new ResponseEntity<>(weather, HttpStatus.OK);
    }
}
