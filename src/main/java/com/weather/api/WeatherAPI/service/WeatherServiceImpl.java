package com.weather.api.WeatherAPI.service;

import com.weather.api.WeatherAPI.dao.PincodeRepository;
import com.weather.api.WeatherAPI.dao.WeatherRepository;
import com.weather.api.WeatherAPI.entity.Pincode;
import com.weather.api.WeatherAPI.entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService{

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherFeignClient weatherFeignClient;

    @Autowired
    private PincodeFeignClient pincodeFeignClient;

    @Value("${openweather.api.key}")
    private String apiKey;

    private Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Override
    public Weather getWeather(String pincode, long timestamp) {
        // Convert to LocalDate
        LocalDate date = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();
        //If weather is already present in DB
        Optional<Weather> cachedWeather = this.weatherRepository.findByPincodeAndDate(pincode,date);
        if(cachedWeather.isPresent())
            return cachedWeather.get();

        //Fetch or save Pincode
        Optional<Pincode> optionalPincode = this.pincodeRepository.findById(pincode);
        Pincode fetchedPincode;
        if(optionalPincode.isPresent())
            fetchedPincode = optionalPincode.get();
        else{
            fetchedPincode = fetchLatLonDetails(pincode);
            this.pincodeRepository.save(fetchedPincode);
        }

        //Fetch Weather Details
        Weather featchedWeather = fetchWeatherDetails(fetchedPincode.getLatitude(),fetchedPincode.getLongitude(),timestamp);
        featchedWeather.setPincode(pincode);
        featchedWeather.setDate(date);

        return featchedWeather;
    }

    @Override
    public Weather getWeatherByZip(String pincode) {
        //If weather is already present in DB
        Optional<Weather> cachedWeather = this.weatherRepository.findByPincode(pincode);
        if(cachedWeather.isPresent()){
            logger.info("Weather Details already present in DB for pincode " + pincode);
            return cachedWeather.get();
        }
        //Fetch weather by zip code
        Weather fetchedWeather = fetchWeatherDetailsByZip(pincode);
        this.weatherRepository.save(fetchedWeather);
        return fetchedWeather;
    }

    private Weather fetchWeatherDetails(double latitude, double longitude, long timestamp) {
        Map<String,Object> response = this.weatherFeignClient.getWeather(latitude,longitude,timestamp,apiKey);

        List<Map<String,Object>> weatherList = (List<Map<String,Object>>) response.get("weather");
        String weatherDescription = (String) weatherList.get(0).get("description");

        Map<String,Object> mainData = (Map<String,Object>) response.get("main");
        Double temperature = (Double) mainData.get("temp");

        Weather weatherEntity = Weather.builder()
                .weatherDescription(weatherDescription)
                .temparature(temperature)
                .build();

        return weatherEntity;
    }

    public Pincode fetchLatLonDetails(String pincode){
        //Assuming all pincodes to be of India
        String pincodeWithCountryCode = pincode + ",in";
        Map<String,Object> response = this.pincodeFeignClient.getDetails(pincodeWithCountryCode,apiKey);

        Pincode pincodeEntity = new Pincode();
        pincodeEntity.setPincode(pincode);
        pincodeEntity.setLatitude((Double)response.get("lat"));
        pincodeEntity.setLongitude((Double)response.get("lon"));

        return pincodeEntity;
    }
    public Weather fetchWeatherDetailsByZip(String pincode){

        logger.info("Fetching weather for the pincode " + pincode);

        //Assuming all pincodes to be of India
        String pincodeWithCountryCode = pincode + ",in";
        Map<String,Object> response = this.pincodeFeignClient.getDetails(pincodeWithCountryCode,apiKey);

        Pincode pincodeEntity = new Pincode();
        Map<String, Object> coord = (Map<String, Object>) response.get("coord");
        Double latitude = (Double) coord.get("lat");
        Double longitude = (Double) coord.get("lon");
        pincodeEntity.setPincode(pincode);
        pincodeEntity.setLongitude(longitude);
        pincodeEntity.setLatitude(latitude);
        this.pincodeRepository.save(pincodeEntity);

        Weather weather = new Weather();
        List<Map<String,Object>> weatherList = (List<Map<String,Object>>) response.get("weather");
        String weatherDescription = (String) weatherList.get(0).get("description");
        Map<String,Object> mainData = (Map<String,Object>) response.get("main");
        Double temperature = (Double) mainData.get("temp");
        Weather weatherEntity = Weather.builder()
                .weatherDescription(weatherDescription)
                .temparature(temperature)
                .pincode(pincode)
                .date(null)
                .build();
        return weatherEntity;
    }

}
