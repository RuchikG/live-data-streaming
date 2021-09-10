package com.work.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.weather.model.CurrentWeather;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenWeatherService {
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={key}&units=imperial";
    private static final String GEOCODE_URL = "http://api.openweathermap.org/geo/1.0/direct?q={city},{country}&APPID={key}";

    private String apiKey = "581ed42ecb201811150a08d78bb2a7a7";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenWeatherService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public CurrentWeather getCurrentWeather(String city, String country) {
        URI url = new UriTemplate(WEATHER_URL).expand(city, country, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return convert(response);
    }

    private CurrentWeather convert(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            Double deg = root.path("wind").path("deg").asDouble();
            String direction;
            if (deg >= 348.75 || deg <= 11.25) {
                direction = "N";
            } else if (deg >= 11.25 || deg <= 33.75) {
                direction = "NNE";
            } else if (deg >= 33.75 || deg <= 56.25) {
                direction = "NE";
            } else if (deg >= 56.25 || deg <= 78.75) {
                direction = "ENE";
            } else if (deg >= 78.75 || deg <= 101.25) {
                direction = "E";
            } else if (deg >= 101.25 || deg <= 123.75) {
                direction = "ESE";
            } else if (deg >= 123.75 || deg <= 146.25) {
                direction = "SE";
            } else if (deg >= 146.25 || deg <= 168.75) {
                direction = "SSE";
            } else if (deg >= 168.75 || deg <= 191.25) {
                direction = "S";
            } else if (deg >= 191.25 || deg <= 213.75) {
                direction = "SSW";
            } else if (deg >= 213.75 || deg <= 236.25) {
                direction = "SW";
            } else if (deg >= 236.25 || deg <= 258.75) {
                direction = "WSW";
            } else if (deg >= 258.75 || deg <= 281.25) {
                direction = "W";
            } else if (deg >= 281.25 || deg <= 303.75) {
                direction = "WNW";
            } else if (deg >= 303.75 || deg <= 326.25) {
                direction = "NW";
            } else if (deg >= 326.25 || deg <= 348.75) {
                direction = "NNW";
            } else {
                direction = "N";
            }
            return new CurrentWeather(root.path("weather").get(0).path("description").asText(),
                    BigDecimal.valueOf(root.path("main").path("temp").asDouble()),
                    BigDecimal.valueOf(root.path("main").path("feels_like").asDouble()),
                    BigDecimal.valueOf(root.path("wind").path("speed").asDouble()), direction);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    public Map<String, String> getLatLong(String city, String country) {
        URI url = new UriTemplate(GEOCODE_URL).expand(city, country, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return convertGeo(response);
    }

    private Map<String, String> convertGeo(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            Map<String, String> map = new HashMap<>();
            map.put("lat", root.get(0).path("lat").asText());
            map.put("long", root.get(0).path("lon").asText());
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}
