package com.antonioallen.cnnweather.request.listener;

import com.antonioallen.cnnweather.objects.CityObject;
import com.antonioallen.cnnweather.objects.WeatherObject;
import com.antonioallen.cnnweather.request.RequestError;

import java.util.ArrayList;

/**
 * Created by antonioallen on 11/3/16.
 */

public interface OnWeatherListener {
    void onCurrentWeather(WeatherObject currentWeather, CityObject cityObject);
    void onForecast(ArrayList<WeatherObject> weatherObjects, CityObject cityObject, int dayCount);
    void onError(RequestError error);
}
