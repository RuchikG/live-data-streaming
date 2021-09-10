package com.work.weather.model;

import java.io.Serializable;

public class CurrentAstronomy implements Serializable {

    private Double moonFraction;
    private String currentMoonPhase;
    private String closestMoonPhase;
    private String moonRise;
    private String moonSet;
    private String sunRise;
    private String sunSet;

    public CurrentAstronomy(Double moonFraction, String currentMoonPhase, String closestMoonPhase, String moonRise, String moonSet, String sunRise, String sunSet) {
        this.moonFraction = moonFraction;
        this.currentMoonPhase = currentMoonPhase;
        this.closestMoonPhase = closestMoonPhase;
        this.moonRise = moonRise;
        this.moonSet = moonSet;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
    }

    public Double getMoonFraction() {
        return moonFraction;
    }

    public void setMoonFraction(Double moonFraction) {
        this.moonFraction = moonFraction;
    }

    public String getCurrentMoonPhase() {
        return currentMoonPhase;
    }

    public void setCurrentMoonPhase(String currentMoonPhase) {
        this.currentMoonPhase = currentMoonPhase;
    }

    public String getClosestMoonPhase() {
        return closestMoonPhase;
    }

    public void setClosestMoonPhase(String closestMoonPhase) {
        this.closestMoonPhase = closestMoonPhase;
    }

    public String getMoonRise() {
        return moonRise;
    }

    public void setMoonRise(String moonRise) {
        this.moonRise = moonRise;
    }

    public String getMoonSet() {
        return moonSet;
    }

    public void setMoonSet(String moonSet) {
        this.moonSet = moonSet;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }
}
