package com.example.smartagriculture.bean;

import cjh.weatherviewlibarary.IBaseWeatherData;

/**
 * Created by cjh on 16-11-23.
 */

public class MyWeatherData implements IBaseWeatherData {

    private int highDegree;
    private int lowDegree;
    public String date;

    public MyWeatherData(int highDegree, int lowDegree, String date, String weatherUrl, String wea, String win_speed, String win, String air_level) {
        this.highDegree = highDegree;
        this.lowDegree = lowDegree;
        this.date = date;
        this.weatherUrl = weatherUrl;
        this.wea = wea;
        this.win_speed = win_speed;
        this.win = win;
        this.air_level = air_level;
    }

    public String weatherUrl;
    public String wea;
    public String win_speed;
    public String win;
    public String air_level;

    public MyWeatherData() {
    }

    public MyWeatherData(int highDegree, int lowDegree, String date) {
        this.highDegree = highDegree;
        this.lowDegree = lowDegree;
        this.date = date;
    }

    @Override
    public int getHighDegree() {
        return highDegree;
    }

    @Override
    public int getLowDegree() {
        return lowDegree;
    }
}
