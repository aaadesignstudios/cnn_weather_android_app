package com.antonioallen.cnnweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.antonioallen.cnnweather.abstraction.CNNConstants;
import com.antonioallen.cnnweather.objects.CityObject;
import com.antonioallen.cnnweather.objects.WeatherObject;
import com.antonioallen.cnnweather.request.GetWeather;
import com.antonioallen.cnnweather.request.RequestError;
import com.antonioallen.cnnweather.request.builder.RequestBuilder;
import com.antonioallen.cnnweather.request.listener.OnWeatherListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Action Bar
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.app_name_space));
        }


    }

}
