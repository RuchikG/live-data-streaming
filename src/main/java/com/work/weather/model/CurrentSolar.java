package com.work.weather.model;

public class CurrentSolar {
    private String time;
    private Double uvIndex;
    private Double downwardShortWaveRadiationFlux;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public Double getDownwardShortWaveRadiationFlux() {
        return downwardShortWaveRadiationFlux;
    }

    public void setDownwardShortWaveRadiationFlux(Double downwardShortWaveRadiationFlux) {
        this.downwardShortWaveRadiationFlux = downwardShortWaveRadiationFlux;
    }

    public CurrentSolar(String time, Double uvIndex, Double downwardShortWaveRadiationFlux) {
        this.time = time;
        this.uvIndex = uvIndex;
        this.downwardShortWaveRadiationFlux = downwardShortWaveRadiationFlux;
    }
}
