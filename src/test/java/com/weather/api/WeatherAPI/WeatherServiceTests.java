//package com.weather.api.WeatherAPI;
//
//import com.weather.api.WeatherAPI.dao.PincodeRepository;
//import com.weather.api.WeatherAPI.dao.WeatherRepository;
//import com.weather.api.WeatherAPI.entity.Weather;
//import com.weather.api.WeatherAPI.service.WeatherService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class WeatherServiceTests {
//
//    @Autowired
//    private WeatherService weatherService;
//
//    @MockitoBean
//    private PincodeRepository pincodeRepository;
//
//    @MockitoBean
//    private WeatherRepository weatherRepository;
//
//    @Test
//    public void shouldReturnCachedWeatherWhenAvailable() {
//        // Mock the repository behavior
////        when(weatherRepository.findByPincodeAndDate(anyString(), any()))
////                .thenReturn(Optional.of(new Weather("Clear sky", 25.0)));
////
////        Weather result = weatherService.getWeather("411014", LocalDate.of(2020, 10, 15));
////
////        assertEquals("Clear sky", result.getWeatherDescription());
//    }
//
//}
