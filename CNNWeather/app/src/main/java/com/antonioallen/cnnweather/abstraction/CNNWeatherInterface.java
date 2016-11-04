package com.antonioallen.cnnweather.abstraction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.antonioallen.cnnweather.objects.WeatherObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by antonioallen on 11/3/16.
 */

public interface CNNWeatherInterface {
    //Filter Weather Objects Returned
    ArrayList<WeatherObject> filterWeather(ArrayList<WeatherObject> weatherObjects);
    //Navigate to Details Activity
    void navToDetailsActivity(@NonNull Context context, @NonNull WeatherObject weatherObject);
    boolean isTomorrow(Date date);
    boolean isToday(Date date);
    //Get Weather Icon Drawable Resource Id
    int getImageDrawableResourceId(String code, boolean icon);
    //Get Wind Direction Based Off Degrees
    String getWindDirection(double degree);
}
