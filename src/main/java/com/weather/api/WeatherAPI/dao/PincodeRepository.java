package com.weather.api.WeatherAPI.dao;

import com.weather.api.WeatherAPI.entity.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PincodeRepository extends JpaRepository<Pincode,String> {
}
