package com.work.weather.model;

public class CurrentExtremeTide {
    private Double height;
    private String time;
    private String type;

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CurrentExtremeTide(Double height, String time, String type) {
        this.height = height;
        this.time = time;
        this.type = type;
    }
}
