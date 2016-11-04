package com.antonioallen.cnnweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by antonioallen on 11/3/16.
 */

public class BaseActivity extends AppCompatActivity {

    private AppCompatActivity activity;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public Context getContext() {
        return context;
    }
}
