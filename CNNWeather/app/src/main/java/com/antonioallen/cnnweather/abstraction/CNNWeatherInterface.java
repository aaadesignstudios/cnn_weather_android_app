package com.antonioallen.cnnweather.abstraction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.antonioallen.cnnweather.objects.WeatherObject;

import java.util.Date;

/**
 * Created by antonioallen on 11/3/16.
 */

public interface CNNWeatherInterface {
    void navToDetailsActivity(@NonNull Context context, @NonNull WeatherObject weatherObject);
    boolean isTomorrow(Date date);
    int getImageDrawableResourceId(String code, boolean icon);
}
