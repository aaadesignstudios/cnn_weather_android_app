package com.antonioallen.cnnweather.abstraction;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.antonioallen.cnnweather.DetailActivity;
import com.antonioallen.cnnweather.R;
import com.antonioallen.cnnweather.objects.WeatherObject;

import java.util.Calendar;
import java.util.Date;
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
    public void navToDetailsActivity(@NonNull Context context, @NonNull WeatherObject weatherObject) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_EXTRA_WEATHER_OBJ, weatherObject);
        context.startActivity(intent);
    }

    @Override
    public boolean isTomorrow(Date date) {
        Date createdDate;
        Calendar time  = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
        createdDate = time.getTime();

        long timeStampDifference = date.getTime() - createdDate.getTime();
        long daysDifference = TimeUnit.MILLISECONDS.toDays(timeStampDifference);
        Log.d(TAG, "Days Difference: "+String.valueOf(daysDifference));
        return daysDifference < 1;
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
}
