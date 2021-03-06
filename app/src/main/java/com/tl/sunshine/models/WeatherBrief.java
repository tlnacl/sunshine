package com.tl.sunshine.models;

/**
 * Created by tomtang on 6/05/15.
 */
public class WeatherBrief {
    float temp;
    float high;
    float low;
    int weatherId;

    public WeatherBrief() {

    }

    public WeatherBrief(float temp, float high, float low, int weatherId) {
        this.temp = temp;
        this.high = high;
        this.low = low;
        this.weatherId = weatherId;
    }

    public float getTemp() {
        return temp;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public int getWeatherId() {
        return weatherId;
    }
}
