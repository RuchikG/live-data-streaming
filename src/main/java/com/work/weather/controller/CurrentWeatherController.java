package com.work.weather.controller;

import com.work.weather.model.CurrentAstronomy;
import com.work.weather.model.CurrentExtremeTide;
import com.work.weather.model.CurrentSolar;
import com.work.weather.model.CurrentWeather;
import com.work.weather.service.OpenWeatherService;
import com.work.weather.service.StormGlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class CurrentWeatherController {

    @Autowired
    private OpenWeatherService openWeatherService;

    @Autowired
    private StormGlassService stormGlassService;

    @RequestMapping(value = "/current-weather", method = RequestMethod.GET)
    public ModelAndView getCurrentWeather(ModelAndView modelAndView, @RequestParam("city") String city, @RequestParam("country") String country) {
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