package com.antonioallen.cnnweather;

import com.antonioallen.cnnweather.request.queue.WeatherRequestQueue;

/**
 * Created by antonioallen on 11/3/16.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //Stop Request Queue on Application Quit
        WeatherRequestQueue.getInstance(getApplicationContext()).stop();
    }

}
