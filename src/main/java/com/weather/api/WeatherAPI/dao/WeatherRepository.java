package com.weather.api.WeatherAPI.dao;

import com.weather.api.WeatherAPI.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    Optional<Weather> findByPincodeAndDate(String pincode, LocalDate date);

    Optional<Weather> findByPincode(String pincode);
}
