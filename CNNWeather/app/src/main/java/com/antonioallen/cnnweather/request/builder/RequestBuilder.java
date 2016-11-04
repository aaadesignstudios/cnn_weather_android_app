package com.antonioallen.cnnweather.request.builder;

import android.util.Log;

/**
 * Created by antonioallen on 11/3/16.
 */

public class RequestBuilder {
    /**
     * Request Builder to Create URL
     */

    public static String TAG = RequestBuilder.class.getSimpleName();
    String city, state;
    String apiKey;

    public RequestBuilder(){

    }

    public String getState() {
        return state;
    }

    public RequestBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RequestBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public RequestBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getForecastUrl(){
        boolean valid = true;
        if (city == null || city.isEmpty()){
            Log.d(TAG, "Please provide a valid city");
            valid = false;
        }
        if (state == null || state.isEmpty()){
            Log.d(TAG, "Please provide a valid state");
            valid = false;
        }
        if (apiKey == null || apiKey.isEmpty()){
            Log.d(TAG, "Please provide a valid api key");
            valid = false;
        }
        if (!valid){
            return null;
        }else {
            return "http://api.openweathermap.org/data/2.5/forecast/daily?q="+
                    city+","+state+"&units=imperial&cnt=5&APPID="+apiKey+"";
        }
    }

    public String getCurrentUrl(){
        boolean valid = true;
        if (city == null || city.isEmpty()){
            Log.d(TAG, "Please provide a valid city");
            valid = false;
        }
        if (state == null || state.isEmpty()){
            Log.d(TAG, "Please provide a valid state");
            valid = false;
        }
        if (apiKey == null || apiKey.isEmpty()){
            Log.d(TAG, "Please provide a valid api key");
            valid = false;
        }
        if (!valid){
            return null;
        }else {
            return "http://api.openweathermap.org/data/2.5/weather?q="+
                    city+","+state+"&units=imperial&APPID="+apiKey+"";
        }
    }
}
