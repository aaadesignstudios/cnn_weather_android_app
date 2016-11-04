package com.antonioallen.cnnweather.request.queue;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by antonioallen on 11/3/16.
 */

public class WeatherRequestQueue {
    private static WeatherRequestQueue instance;
    private RequestQueue requestQueue;
    private Network network;
    private boolean started;
    public WeatherRequestQueue(Context context){
        network = new BasicNetwork(new HurlStack());
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
        requestQueue = new RequestQueue(cache, network);
    }

    public static WeatherRequestQueue getInstance(Context context) {
        if (instance == null){
            instance = new WeatherRequestQueue(context);
            return instance;
        }
        return instance;
    }

    public RequestQueue getQueue() {
        if (!started){
            requestQueue.start();
            started = true;
        }
        return requestQueue;
    }

    public void stop(){
        if (requestQueue == null || !started)
            return;
        requestQueue.stop();
    }
}
