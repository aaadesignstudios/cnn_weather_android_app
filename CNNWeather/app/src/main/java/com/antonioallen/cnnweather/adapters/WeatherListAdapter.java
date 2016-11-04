package com.antonioallen.cnnweather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antonioallen.cnnweather.R;
import com.antonioallen.cnnweather.abstraction.CNNWeather;
import com.antonioallen.cnnweather.objects.WeatherObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by antonioallen on 11/3/16.
 */

public class WeatherListAdapter extends ArrayAdapter<WeatherObject> {

    private Context context;
    private List<WeatherObject> weatherObjects;

    public WeatherListAdapter(Context context, List<WeatherObject> objects) {
        super(context, -1, objects);
        this.context = context;
        this.weatherObjects = objects;

    }

    @Override
    public void add(WeatherObject object) {
        weatherObjects.add(object);
        notifyDataSetChanged();
    }

    @Override
    public void remove(WeatherObject object) {
        weatherObjects.remove(object);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        weatherObjects.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<WeatherObject> objects){
        weatherObjects.addAll(objects);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeatherObject weatherObject = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_day_list_view, parent, false);
        }

        //Get Views
        TextView tvWeatherDay = (TextView) convertView.findViewById(R.id.tv_day);
        TextView tvWeatherMain = (TextView) convertView.findViewById(R.id.tv_weather_main);
        TextView tvWeatherMin = (TextView) convertView.findViewById(R.id.tv_temp_min);
        TextView tvWeatherMax = (TextView) convertView.findViewById(R.id.tv_temp_max);
        ImageView imgvWeatherIcon = (ImageView) convertView.findViewById(R.id.imgv_weather_image);

        if (weatherObject != null){
            Date weatherDate = new Date(weatherObject.getTimeStamp() * 1000);
            SimpleDateFormat format = new SimpleDateFormat("EEEE");
            String stringDay = format.format(weatherDate);
            if (CNNWeather.getInstance().isTomorrow(weatherDate)){
                stringDay = "Tomorrow";
            }
            tvWeatherDay.setText(stringDay);
            tvWeatherMain.setText(weatherObject.getWeatherMain());
            tvWeatherMax.setText(String.valueOf((int) weatherObject.getHighTemperature() + context.getString(R.string.degree_symbol)));
            tvWeatherMin.setText(String.valueOf((int) weatherObject.getLowTemperature() + context.getString(R.string.degree_symbol)));
            imgvWeatherIcon.setImageDrawable(ContextCompat.getDrawable(getContext(),
                    CNNWeather.getInstance().getImageDrawableResourceId(weatherObject.getWeatherIcon(), true)));
        }
        return convertView;
    }

}
