package com.work.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.weather.model.CurrentAstronomy;
import com.work.weather.model.CurrentExtremeTide;
import com.work.weather.model.CurrentSolar;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class StormGlassService {
    private static final String ASTRONOMY_URL = "https://api.stormglass.io/v2/astronomy/point?lat={lat}&lng={long}";
    private static final String TIDE_URL = "https://api.stormglass.io/v2/astronomy/point?lat={lat}&lng={long}";
    private static final String SOLAR_URL = "https://api.stormglass.io/v2/solar/point?lat={lat}&lng={long}&params=uvIndex,downwardShortWaveRadiationFlux";

    private final String apiKey = "c98f6874-0f68-11ec-8a89-0242ac130002-c98f68ec-0f68-11ec-8a89-0242ac130002";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public StormGlassService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public CurrentAstronomy getAstronomy(String lat, String lon) {
        URI url = new UriTemplate(ASTRONOMY_URL).expand(lat, lon);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return convertAstronomy(response);
    }

    private CurrentAstronomy convertAstronomy(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new CurrentAstronomy(root.path("data").get(0).path("moonFraction").asDouble(),
                    root.path("data").get(0).path("moonPhase").path("current").path("text").asText(),
                    root.path("data").get(0).path("moonPhase").path("closest").path("text").asText(),
                    root.path("data").get(0).path("moonrise").asText(),
                    root.path("data").get(0).path("moonset").asText(),
                    root.path("data").get(0).path("sunrise").asText(),
                    root.path("data").get(0).path("sunset").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    public CurrentExtremeTide getTide(String lat, String lon) {
        URI url = new UriTemplate(TIDE_URL).expand(lat, lon);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return convertTide(response);
    }

    private CurrentExtremeTide convertTide(ResponseEntity<String> response) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new CurrentExtremeTide(root.path("data").get(0).path("height").asDouble(),
                    root.path("data").get(0).path("time").asText(),
                    root.path("data").get(0).path("type").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    public CurrentSolar getSolar(String lat, String lon) {
        URI url = new UriTemplate(SOLAR_URL).expand(lat, lon);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return convertSolar(response);
    }

    private CurrentSolar convertSolar(ResponseEntity<String> response){
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return new CurrentSolar(root.path("data").get(0).path("time").asText(),
                    ((root.path("data").get(0).path("uvIndex").path("noaa").asDouble()) + (root.path("data").get(0).path("uvIndex").path("sg").asDouble())) / 2,
                    ((root.path("data").get(0).path("uvIndex").path("noaa").asDouble()) + (root.path("data").get(0).path("uvIndex").path("sg").asDouble())) / 2);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}
