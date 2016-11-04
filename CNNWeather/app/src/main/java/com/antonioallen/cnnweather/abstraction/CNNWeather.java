package com.antonioallen.cnnweather.abstraction;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.antonioallen.cnnweather.DetailActivity;
import com.antonioallen.cnnweather.R;
import com.antonioallen.cnnweather.objects.WeatherObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by antonioallen on 11/3/16.
 */

public class CNNWeather implements CNNWeatherInterface, CNNConstants {

    private static final String TAG = CNNWeather.class.getSimpleName();
    private static CNNWeather instance;


    public CNNWeather(){

    }

    public static CNNWeather getInstance(){
        if (instance == null)
            instance = new CNNWeather();
        return instance;
    }

    @Override
    public ArrayList<WeatherObject> filterWeather(ArrayList<WeatherObject> weatherObjects) {
        /**
         * Open Weather Api is updated every 3 hours and sometimes returns previous or current days.
         * Use this method to filter those out.
         */

        Date currentDate = new Date();
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        int currentDayOfMonth = Integer.valueOf(dayFormat.format(currentDate));
        while (containInvalidDays(currentDayOfMonth, weatherObjects)){
            weatherObjects = filteredWeather(currentDayOfMonth, weatherObjects);
        }
        return weatherObjects;
    }

    @Override
    public void navToDetailsActivity(@NonNull Context context, @NonNull WeatherObject weatherObject) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_EXTRA_WEATHER_OBJ, weatherObject);
        context.startActivity(intent);
    }

    @Override
    public boolean isTomorrow(Date weatherDate) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        Date currentDate = new Date();
        int currentDayOfMonth = Integer.valueOf(dayFormat.format(currentDate));
        int weatherDateDayOfMonth = Integer.valueOf(dayFormat.format(weatherDate));
        return weatherDateDayOfMonth - currentDayOfMonth == 1;
    }

    @Override
    public boolean isToday(Date weatherDate) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        Date currentDate = new Date();
        int currentDayOfMonth = Integer.valueOf(dayFormat.format(currentDate));
        int weatherDateDayOfMonth = Integer.valueOf(dayFormat.format(weatherDate));
        return weatherDateDayOfMonth - currentDayOfMonth == 0;
    }

    @Override
    public int getImageDrawableResourceId(String code, boolean icon) {
        if (code == null || code.length() != 3)
            return R.drawable.ic_error_transparent;

        String substring = code.substring(0, 2);

        switch (substring){
            case ImageArtworkCodes._01:
                return ((!icon))? R.drawable.art_clear : R.drawable.ic_clear;
            case ImageArtworkCodes._02:
                return ((!icon))? R.drawable.art_light_clouds : R.drawable.ic_light_clouds;
            case ImageArtworkCodes._03:
                return ((!icon))? R.drawable.art_clouds : R.drawable.ic_cloudy;
            case ImageArtworkCodes._04:
                return ((!icon))? R.drawable.art_clouds : R.drawable.ic_cloudy;
            case ImageArtworkCodes._09:
                return ((!icon))? R.drawable.art_light_rain : R.drawable.ic_light_rain;
            case ImageArtworkCodes._10:
                return ((!icon))? R.drawable.art_rain : R.drawable.ic_rain;
            case ImageArtworkCodes._11:
                return ((!icon))? R.drawable.art_storm : R.drawable.ic_storm;
            case ImageArtworkCodes._13:
                return ((!icon))? R.drawable.art_snow : R.drawable.ic_snow;
            case ImageArtworkCodes._50:
                return ((!icon))? R.drawable.art_fog : R.drawable.ic_fog;
            default:
                return R.drawable.ic_error_transparent;
        }
    }

    @Override
    public String getWindDirection(double degree) {
        return WEATHER_DIRECTIONS[ (int)Math.round((((double)degree % 360) / 45)) % 8 ];
    }

    private ArrayList<WeatherObject> filteredWeather(int currentDayOfMonth, ArrayList <WeatherObject> weatherObjects){
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        for (int i = 0; i < weatherObjects.size(); i++){
            WeatherObject weatherObject = weatherObjects.get(i);
            Date weatherDate = new Date(weatherObject.getTimeStamp() * DATE_TIME_CONVERSION);
            int weatherDateDayOfMonth = Integer.valueOf(dayFormat.format(weatherDate));
            if (weatherDateDayOfMonth < currentDayOfMonth){
                Log.d(TAG, "Day is less than current day. Remove.");
                weatherObjects.remove(i);
                break;
            }else if (weatherDateDayOfMonth == currentDayOfMonth){
                Log.d(TAG, "Day is same as current day. Remove.");
                weatherObjects.remove(i);
                break;
            }
        }
        return weatherObjects;
    }

    private boolean containInvalidDays(int currentDayOfMonth, ArrayList <WeatherObject> weatherObjects){
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        for (int i = 0; i < weatherObjects.size(); i++){
            WeatherObject weatherObject = weatherObjects.get(i);
            Date weatherDate = new Date(weatherObject.getTimeStamp() * DATE_TIME_CONVERSION);
            int weatherDateDayOfMonth = Integer.valueOf(dayFormat.format(weatherDate));
            if (weatherDateDayOfMonth <= currentDayOfMonth){
                return true;
            }
        }
        return false;
    }

}
