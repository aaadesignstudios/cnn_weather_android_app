package com.antonioallen.cnnweather;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.antonioallen.cnnweather.abstraction.CNNConstants;
import com.antonioallen.cnnweather.abstraction.CNNWeather;
import com.antonioallen.cnnweather.fragments.WeatherDetailFragment;
import com.antonioallen.cnnweather.objects.WeatherObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    WeatherObject weatherObject;
    WeatherDetailFragment weatherDetailFragment;
    private SimpleDateFormat formatDayOfWeek;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        weatherObject = getIntent().getParcelableExtra(CNNConstants.INTENT_EXTRA_WEATHER_OBJ);

        if (weatherObject == null)
            finish();

        Date weatherDate = new Date(weatherObject.getTimeStamp() * 1000);
        formatDayOfWeek = new SimpleDateFormat("EEEE");
        String stringDay = formatDayOfWeek.format(weatherDate);
        if (CNNWeather.getInstance().isTomorrow(weatherDate)){
            stringDay = getString(R.string.text_tomorrow);
        }else if (CNNWeather.getInstance().isToday(weatherDate)){
            stringDay = getString(R.string.text_today);
        }

        //Set Action Bar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(stringDay);
        }

        weatherDetailFragment = WeatherDetailFragment.newInstance(weatherObject);
        // Begin the transaction
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, weatherDetailFragment);
        ft.commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ft != null && weatherDetailFragment != null){
            ft.remove(weatherDetailFragment);
            weatherDetailFragment = null;
            Log.d(TAG, "Detail Fragment Removed");
        }
    }
}
