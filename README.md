# Weather API Project

## Description
This project provides a RESTful API to fetch weather information by pincode. It integrates with the OpenWeather API to fetch weather details and supports fetching weather for:
1. **A specific date** (requires a subscription to OpenWeather’s paid API).
2. **The current day** using the free version of the OpenWeather API.

Although fetching weather for a specific date requires a paid subscription, the corresponding code has been implemented in this project to demonstrate the functionality.
![Screenshot 2024-12-03 021515](https://github.com/user-attachments/assets/e33e7ad7-622d-4105-bc90-ecac339aef52)

---

## Features
- Fetch weather details (description and temperature) for a specific pincode and date.
- Fetch current weather details for a specific pincode.
- Uses caching by storing weather data in the database for previously requested pincodes.

---

## Endpoints

### 1. Fetch Weather by Pincode and Date
**POST** `/api/weather`  
This endpoint fetches the weather for a specific pincode and date.  
**Request Body**:
```json
{
  "pincode": "123456",
  "for_date": "2024-12-01"
}
```

**Response**:
```json
{
  "pincode": "123456",
  "weatherDescription": "clear sky",
  "temperature": 25.0,
  "date": "2024-12-01"
}
```
* Note: This functionality requires a paid OpenWeather API subscription.

### 2. Fetch Current Weather by Pincode
**POST** `/api/weather-zip`
This endpoint fetches the current weather for a specific pincode.
**Request Body**:
```json
{
  "pincode": "123456"
}
```
**Response**:
```json
{
  "pincode": "123456",
  "weatherDescription": "partly cloudy",
  "temperature": 27.5,
  "date": null
}
```
---
## External API Endpoints

The following Feign clients are used to interact with OpenWeather APIs:

### 1. Fetch Pincode Geolocation and Weather Details
**Client**: `PincodeFeignClient`  
**Base URL**: `https://api.openweathermap.org/data/2.5`  
**Endpoint**: `/weather`  
**Request Parameters**:
- **zip**: Pincode with country code (e.g., `123456,in`).
- **appid**: Your OpenWeather API key.

---

### 2. Fetch Weather by Latitude, Longitude, and Timestamp
**Client**: `WeatherFeignClient`  
**Base URL**: `https://api.openweathermap.org/data/`  

**Endpoints**:
1. `/3.0/onecall/timemachine`: For historical weather data.  
   **Request Parameters**:
   - **lat**: Latitude.
   - **lon**: Longitude.
   - **dt**: Unix timestamp for the desired date.
   - **appid**: Your OpenWeather API key.

2. `/2.5/weather`: For current weather by pincode.  
   **Request Parameters**:
   - **zip**: Pincode.
   - **appid**: Your OpenWeather API key.
---
## Postman Results
Below are sample screenshots of API responses tested using Postman:

### 1. Fetching Current Weather for a Pincode
![Screenshot 2024-12-03 024112](https://github.com/user-attachments/assets/2b3a80b3-42be-485d-9729-f8dc3dca48e1)

### 2. Fetching Historical Weather Data for a Pincode
![Screenshot 2024-12-03 024146](https://github.com/user-attachments/assets/e6f3f0a8-f4b4-4aa9-a787-a89792519491)


---

## Database Tables

### 1. Weather Table
Stores weather data for pincode and date.

![Screenshot 2024-12-03 023915](https://github.com/user-attachments/assets/ea5eb21d-0cbd-4e10-ba44-eeb4b067f756)

---

### 2. Pincode Table
Stores geolocation data for pincodes.
![Screenshot 2024-12-03 023928](https://github.com/user-attachments/assets/b38af72b-e15c-456e-a4cf-308d2fa50324)

---

## Technologies Used
* Java: Core language for implementation.
* Spring Boot: Framework for REST API development.
* Feign Clients: For making API calls to OpenWeather and fetching geolocation data.
* MySQL: Database for caching pincode and weather data.
* SLF4J: Logging framework.
---
## How It Works
**1. Weather for a Specific Date**:
  * Converts the date to a Unix timestamp.
  * Checks if the data is cached in the database.
  * If not, retrieves latitude and longitude for the pincode and fetches weather for the given date from OpenWeather.

**2. Current Weather**:
  * Checks if weather data for the pincode is cached.
  * If not, fetches latitude and longitude for the pincode and fetches current weather details from OpenWeather.

**3. Caching**:
  * Pincodes and their corresponding geolocation (latitude and longitude) are cached.
  * Weather data is stored with a pincode and date to avoid redundant API calls.
---
## How to Run
1. Clone the repository and navigate to the project directory.
2. Add your OpenWeather API key in `application.properties`:
```
openweather.api.key=your_api_key_here
```
3. Start the Spring Boot application:
```bash
./mvnw spring-boot:run
```
4. Access the API at `http://localhost:8080/api`.
---
## Limitations
1. Fetching weather for a date requires a paid subscription to OpenWeather.
2. The database should be preconfigured and running.














