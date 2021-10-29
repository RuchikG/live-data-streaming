package com.work.weather.controller;

import com.work.weather.model.*;
import com.work.weather.service.OpenWeatherService;
import com.work.weather.service.StormGlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class CurrentWeatherController {

    @Autowired
    private OpenWeatherService openWeatherService;

    @Autowired
    private StormGlassService stormGlassService;

    @RequestMapping(value = "/currentweather", method = RequestMethod.GET)
    public ModelAndView displayCurrentWeather(ModelAndView modelAndView, WeatherLocation location) {
        modelAndView.addObject("location", location);
        modelAndView.setViewName("city-search");
        return modelAndView;
    }

    @RequestMapping(value = "/currentweather", method = RequestMethod.POST)
    public ModelAndView currentWeather(ModelAndView modelAndView, WeatherLocation location) {
        String city = location.getCity();
        String country = location.getCountryCode();
        CurrentWeather currentWeather = openWeatherService.getCurrentWeather(city, country);
        Map<String, String> coordinates =  openWeatherService.getLatLong(city, country);
        CurrentAstronomy currentAstronomy = stormGlassService.getAstronomy(coordinates.get("lat"), coordinates.get("long"));
        CurrentExtremeTide currentExtremeTide = stormGlassService.getTide(coordinates.get("lat"), coordinates.get("long"));
        CurrentSolar currentSolar = stormGlassService.getSolar(coordinates.get("lat"), coordinates.get("long"));
        modelAndView.addObject("currentWeather", currentWeather);
        modelAndView.addObject("currentAstronomy", currentAstronomy);
        modelAndView.addObject("currentExtremeTide", currentExtremeTide);
        modelAndView.addObject("currentSolar",currentSolar);
        modelAndView.setViewName("current-conditions");
        return modelAndView;
    }
}