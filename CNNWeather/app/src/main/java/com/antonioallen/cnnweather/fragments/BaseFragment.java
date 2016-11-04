package com.antonioallen.cnnweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by antonioallen on 11/4/16.
 */

public class BaseFragment extends Fragment {

    /**
     * Use this fragment to set defaults for all
     * fragments
     * @param savedInstanceState
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
