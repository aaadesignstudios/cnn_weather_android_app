package com.antonioallen.cnnweather.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.antonioallen.cnnweather.R;
import com.antonioallen.cnnweather.abstraction.CNNConstants;
import com.antonioallen.cnnweather.abstraction.CNNWeather;
import com.antonioallen.cnnweather.adapters.WeatherListAdapter;
import com.antonioallen.cnnweather.objects.CityObject;
import com.antonioallen.cnnweather.objects.WeatherObject;
import com.antonioallen.cnnweather.request.GetWeather;
import com.antonioallen.cnnweather.request.RequestError;
import com.antonioallen.cnnweather.request.builder.RequestBuilder;
import com.antonioallen.cnnweather.request.listener.OnWeatherListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnWeatherListener, AdapterView.OnItemClickListener, View.OnClickListener{

    private static final String TAG = MainFragment.class.getSimpleName();

    //Init Views
    private TextView tvCurrentDay, tvCurrentTemp,
            tvCurrentTempMin, tvCurrentWeatherMain;
    private ImageView imgvCurrentWeatherImage;
    private ListView listView;
    private LinearLayout rootLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    //Init Variables
    private WeatherListAdapter mAdapter;
    private WeatherObject currentWeather;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Views
        tvCurrentDay = (TextView) view.findViewById(R.id.tv_current_day);
        tvCurrentTemp = (TextView) view.findViewById(R.id.tv_current_day_temp_current);
        tvCurrentTempMin = (TextView) view.findViewById(R.id.tv_current_day_temp_low);
        tvCurrentWeatherMain = (TextView) view.findViewById(R.id.tv_current_weather_main);
        imgvCurrentWeatherImage = (ImageView) view.findViewById(R.id.imgv_weather_image_current_day);
        listView = (ListView) view.findViewById(R.id.list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        rootLayout = (LinearLayout) view.findViewById(R.id.linl_root_layout);

        //Set Adapter
        mAdapter = new WeatherListAdapter(getContext(), new ArrayList<WeatherObject>());
        listView.setAdapter(mAdapter);

        //Set Listeners
        swipeRefreshLayout.setOnRefreshListener(this);
        rootLayout.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        //Get Weather
        getWeather();
    }

    private void getWeather(){
        if (getActivity() == null)
            return;
        RequestBuilder requestBuilder = new RequestBuilder()
                .setApiKey(CNNConstants.WEATHER_API_KEY)
                .setCity(getString(R.string.default_city))
                .setState(getString(R.string.default_state));
        Runnable r = GetWeather.getWeather(requestBuilder, this, getActivity().getApplicationContext(), true);
        if (r != null){
            r.run();
        }else {
            Toast.makeText(getContext(), CNNConstants.ERROR_UNABLE_TO_GET_WEATHER, Toast.LENGTH_SHORT).show();
        }
    }

    private void setViews(WeatherObject weatherObject){
        if (tvCurrentDay == null || tvCurrentWeatherMain == null || tvCurrentTempMin == null ||
                tvCurrentTemp == null || imgvCurrentWeatherImage == null)
            return;

        Log.d(TAG, "Setting Views");
        Date date = new Date(weatherObject.getTimeStamp() * 1000);
        SimpleDateFormat format = new SimpleDateFormat("MMMM d");
        String currentDate = getString(R.string.text_today_prefix) + format.format(date);
        tvCurrentWeatherMain.setText(weatherObject.getWeatherMain());
        tvCurrentTempMin.setText(String.valueOf((int) weatherObject.getLowTemperature() + getString(R.string.degree_symbol)));
        tvCurrentTemp.setText(String.valueOf((int) weatherObject.getCurrentTemperature() + getString(R.string.degree_symbol)));
        tvCurrentDay.setText(currentDate);
        imgvCurrentWeatherImage.setImageDrawable(ContextCompat.getDrawable(getContext(),
                CNNWeather.getInstance().getImageDrawableResourceId(weatherObject.getWeatherIcon(), false)));
    }

    private void hideRefresh(){
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getWeather();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRefresh() {
        getWeather();
    }


    @Override
    public void onCurrentWeather(WeatherObject currentWeather, CityObject cityObject) {
        //Received Current Weather
        this.currentWeather = currentWeather;
        setViews(currentWeather);
        hideRefresh();
    }

    @Override
    public void onForecast(ArrayList<WeatherObject> weatherObjects, CityObject cityObject, int dayCount) {
        //Received Forecast
        if (mAdapter == null || getActivity() == null)
            return;
        Log.d(TAG, "Weather Forecast Returned");
        mAdapter.clear();
        mAdapter.addAll(CNNWeather.getInstance().filterWeather(weatherObjects));
        hideRefresh();
    }

    @Override
    public void onError(RequestError error) {
        //Request Erro
        Log.d(TAG, "Request Error: "+String.valueOf(error.getMessage()));
        Toast.makeText(getContext(), error.getFriendlyMessage(), Toast.LENGTH_SHORT).show();
        hideRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mAdapter == null || getContext() == null)
            return;
        WeatherObject weatherObject = mAdapter.getItem(i);
        CNNWeather.getInstance().navToDetailsActivity(getContext(), weatherObject);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.linl_root_layout:
                //On Header Clicked
                if (currentWeather != null){
                    CNNWeather.getInstance().navToDetailsActivity(getContext(), currentWeather);
                }
                break;
        }
    }

}
