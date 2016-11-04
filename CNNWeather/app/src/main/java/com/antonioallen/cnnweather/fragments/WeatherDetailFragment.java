package com.antonioallen.cnnweather.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antonioallen.cnnweather.R;
import com.antonioallen.cnnweather.abstraction.CNNConstants;
import com.antonioallen.cnnweather.abstraction.CNNWeather;
import com.antonioallen.cnnweather.objects.WeatherObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.format;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends Fragment {
    private static final String ARGS_WEATHER_OBJ = "ARG_WEATHER_OBJ";
    private WeatherObject weatherObject;
    private SimpleDateFormat formatE;
    private SimpleDateFormat formatMD;
    NumberFormat decimalFormat;


    //Init Views
    TextView tvDay, tvDate, tvWeatherTempMin, tvWeatherTempMax,
            tvWeatherMain, tvWeatherHumidity, tvWeatherPressure,
            tvWeatherWind;
    ImageView imgvWeatherIcon;

    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    //Create Instance Of Detail Fragment
    public static WeatherDetailFragment newInstance(WeatherObject weatherObject) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_WEATHER_OBJ, weatherObject);
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        weatherObject = args.getParcelable(ARGS_WEATHER_OBJ);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set Vars
        decimalFormat = new DecimalFormat("#0.00");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Views
        tvDay = (TextView) view.findViewById(R.id.tv_detail_day);
        tvDate = (TextView) view.findViewById(R.id.tv_detail_date);
        tvWeatherTempMax = (TextView) view.findViewById(R.id.tv_detail_temp_high);
        tvWeatherTempMin = (TextView) view.findViewById(R.id.tv_detail_temp_low);
        tvWeatherMain = (TextView) view.findViewById(R.id.tv_detail_weather_main);
        tvWeatherHumidity = (TextView) view.findViewById(R.id.tv_detail_humidity);
        tvWeatherPressure = (TextView) view.findViewById(R.id.tv_detail_pressure);
        tvWeatherWind = (TextView) view.findViewById(R.id.tv_detail_wind);
        imgvWeatherIcon = (ImageView) view.findViewById(R.id.imgv_detail_weather_image);



        //Set Content
        if (weatherObject == null)
            return;
        Date weatherDate = new Date(weatherObject.getTimeStamp() * 1000);
        formatE = new SimpleDateFormat("EEEE");
        formatMD = new SimpleDateFormat("MMMM d");
        String stringDay = formatE.format(weatherDate);
        if (CNNWeather.getInstance().isTomorrow(weatherDate)){
            stringDay = "Tomorrow";
        }
        double kilometersPerHour = (weatherObject.getWindSpeed() * CNNConstants.MPH_TO_KPH_CONVERSION_RATE);

        //TODO GET DIRECTION FROM DEGREES

        tvDay.setText(stringDay);
        tvDate.setText(formatMD.format(weatherDate));
        tvWeatherTempMax.setText(String.valueOf((int) weatherObject.getHighTemperature() +
                getContext().getString(R.string.degree_symbol)));
        tvWeatherTempMin.setText(String.valueOf((int) weatherObject.getLowTemperature() +
                getContext().getString(R.string.degree_symbol)));
        tvWeatherMain.setText(weatherObject.getWeatherMain());
        tvWeatherHumidity.setText(String.valueOf(getString(R.string.text_humidity_prefix) +
                String.valueOf(weatherObject.getHumidityPercentage()) +
                getString(R.string.percentage_symbol)));
        tvWeatherPressure.setText(String.valueOf(getString(R.string.text_pressure_prefix) +
                String.valueOf(weatherObject.getHumidityPercentage()) +
                getString(R.string.pressure_symbol)));
        tvWeatherWind.setText(String.valueOf(getString(R.string.text_wind_prefix) +
                decimalFormat.format(kilometersPerHour) +
                getString(R.string.wind_symbol) + " "+
                getWindDirection(weatherObject.getWindDirectionDegrees())));

    }

    private String getWindDirection(double degree){
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        return directions[ (int)Math.round((  ((double)degree % 360) / 45)) % 8 ];
    }

}
