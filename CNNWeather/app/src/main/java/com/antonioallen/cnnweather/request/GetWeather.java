package com.antonioallen.cnnweather.request;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antonioallen.cnnweather.abstraction.CNNConstants;
import com.antonioallen.cnnweather.objects.CityObject;
import com.antonioallen.cnnweather.objects.WeatherObject;
import com.antonioallen.cnnweather.request.builder.RequestBuilder;
import com.antonioallen.cnnweather.request.listener.OnWeatherListener;
import com.antonioallen.cnnweather.request.queue.WeatherRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by antonioallen on 11/3/16.
 */

public class GetWeather implements CNNConstants{
    private static String TAG  = GetWeather.class.getSimpleName();
    private static String THREAD_NAME  = GetWeather.class.getSimpleName()+"_THREAD";
    private static ThreadGroup threadGroup = new ThreadGroup(THREAD_NAME);

    /**
     * Get Weather Data in Simple Fashion
     * @param requestBuilder
     * @param listener
     * @param context
     * @param asynchronous
     * @return
     */

    public static Runnable getWeather(@NonNull final RequestBuilder requestBuilder,
                                      @NonNull final OnWeatherListener listener, @NonNull final Context context, boolean asynchronous){
        //Check to make sure URL is Valid
        if (requestBuilder.getForecastUrl() == null || requestBuilder.getCurrentUrl() == null)
            return null;

        Log.d(TAG, "Forecast Weather Url: "+String.valueOf(requestBuilder.getForecastUrl()));
        Log.d(TAG, "Current Weather Url: "+String.valueOf(requestBuilder.getCurrentUrl()));

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest requestForecast = new JsonObjectRequest(Request.Method.GET,
                        requestBuilder.getForecastUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Get Data
                        if (response != null){
                            Log.d(TAG, "Forecast Response: "+response.toString());
                            if (response.has(JsonWeatherResponseKeys.JSON_INT_CODE)){
                                try {
                                    int code = response.getInt(JsonWeatherResponseKeys.JSON_INT_CODE);
                                    if (code != CODE_SUCCESS){
                                        listener.onError(new RequestError("Response code was not successful: "
                                                + String.valueOf(code), ERROR_UNABLE_TO_GET_WEATHER));
                                        return;
                                    }
                                    //Initialize Retur Objects
                                    ArrayList<WeatherObject> weatherObjects = null;
                                    CityObject cityObject = null;

                                    JSONObject cityObj = ((response.has(JsonWeatherResponseKeys.JSON_OBJ_CITY)))?
                                            response.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_CITY) : null;

                                    if (cityObj == null){
                                        listener.onError(new RequestError("City Object Null", ERROR_UNABLE_TO_GET_WEATHER));
                                        return;
                                    }

                                    double cityLatitude = 0.0, cityLongitude = 0.0;

                                    int cityId = ((cityObj.has(JsonWeatherResponseKeys.JSON_INT_CITY_ID)))?
                                            cityObj.getInt(JsonWeatherResponseKeys.JSON_INT_CITY_ID) : 0;

                                    String cityName = ((cityObj.has(JsonWeatherResponseKeys.JSON_STRING_CITY_NAME)))?
                                            cityObj.getString(JsonWeatherResponseKeys.JSON_STRING_CITY_NAME) : null;

                                    JSONObject coordObj = ((cityObj.has(JsonWeatherResponseKeys.JSON_OBJ_CITY_COORDINATES)))?
                                            cityObj.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_CITY_COORDINATES) : null;

                                    if (coordObj != null){
                                        cityLatitude = ((coordObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LATITUDE)))?
                                                coordObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LATITUDE) : 0.0;

                                        cityLongitude = ((coordObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LONGITUDE)))?
                                                coordObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LONGITUDE) : 0.0;
                                    }

                                    String cityCountry = ((cityObj.has(JsonWeatherResponseKeys.JSON_STRING_CITY_COUNTRY)))?
                                            cityObj.getString(JsonWeatherResponseKeys.JSON_STRING_CITY_COUNTRY) : null;

                                    int cityPopulation = ((cityObj.has(JsonWeatherResponseKeys.JSON_INT_CITY_POPULATION)))?
                                            cityObj.getInt(JsonWeatherResponseKeys.JSON_INT_CITY_POPULATION) : 0;

                                    //Create City Object
                                    cityObject = new CityObject(cityName, cityCountry);
                                    cityObject.setId(cityId);
                                    cityObject.setLatitude(cityLatitude);
                                    cityObject.setLongitude(cityLongitude);
                                    cityObject.setPopulation(cityPopulation);

                                    int daysCount = ((response.has(JsonWeatherResponseKeys.JSON_INT_COUNT)))?
                                            response.getInt(JsonWeatherResponseKeys.JSON_INT_COUNT) : 0;

                                    JSONArray listArray = ((response.has(JsonWeatherResponseKeys.JSON_ARRAY_LIST)))?
                                            response.getJSONArray(JsonWeatherResponseKeys.JSON_ARRAY_LIST) : null;

                                    if (listArray != null){
                                       weatherObjects = new ArrayList<>();

                                        for (int i = 0; i < listArray.length(); i++){
                                            JSONObject weatherObj = listArray.getJSONObject(i);
                                            if (weatherObj != null){
                                                long timeStamp = ((weatherObj.has(JsonWeatherResponseKeys.JSON_LONG_DATETIME)))?
                                                        weatherObj.getLong(JsonWeatherResponseKeys.JSON_LONG_DATETIME) : 0;

                                                JSONObject tempObj = ((weatherObj.has(JsonWeatherResponseKeys.JSON_OBJ_TEMP)))?
                                                        weatherObj.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_TEMP) : null;

                                                if (tempObj == null){
                                                    continue;
                                                }

                                                double currentTemp = ((tempObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_DAY)))?
                                                        tempObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_DAY) : 0.0;

                                                double minTemp = ((tempObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_MIN)))?
                                                        tempObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_MIN) : 0.0;

                                                double maxTemp = ((tempObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_MAX)))?
                                                        tempObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_MAX) : 0.0;

                                                double nightTemp = ((tempObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_NIGHT)))?
                                                        tempObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_NIGHT) : 0.0;

                                                double eveTemp = ((tempObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_EVENING)))?
                                                        tempObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_EVENING) : 0.0;

                                                double mornTemp = ((tempObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_MORNING)))?
                                                        tempObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_MORNING) : 0.0;

                                                double pressure = ((weatherObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_PRESSURE)))?
                                                        weatherObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_PRESSURE) : 0.0;

                                                double humidity = ((weatherObj.has(JsonWeatherResponseKeys.JSON_INT_HUMIDITY)))?
                                                        weatherObj.getDouble(JsonWeatherResponseKeys.JSON_INT_HUMIDITY) : 0.0;

                                                JSONArray weatherArray = ((weatherObj.has(JsonWeatherResponseKeys.JSON_ARRAY_WEATHER)))?
                                                        weatherObj.getJSONArray(JsonWeatherResponseKeys.JSON_ARRAY_WEATHER) : null;

                                                if (weatherArray == null || weatherArray.length() == 0){
                                                    continue;
                                                }

                                                // Get the first object in array
                                                JSONObject weatherDetailsObj = weatherArray.getJSONObject(0);

                                                if (weatherDetailsObj == null){
                                                    continue;
                                                }

                                                int weatherId = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_INT_WEATHER_ID)))?
                                                        weatherDetailsObj.getInt(JsonWeatherResponseKeys.JSON_INT_WEATHER_ID) : 0;

                                                String weatherMain = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_STRING_WEATHER_MAIN)))?
                                                        weatherDetailsObj.getString(JsonWeatherResponseKeys.JSON_STRING_WEATHER_MAIN) : null;

                                                String weatherDescription = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_STRING_WEATHER_DESCRIPTION)))?
                                                        weatherDetailsObj.getString(JsonWeatherResponseKeys.JSON_STRING_WEATHER_DESCRIPTION) : "";

                                                String weatherIcon = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_STRING_WEATHER_ICON)))?
                                                        weatherDetailsObj.getString(JsonWeatherResponseKeys.JSON_STRING_WEATHER_ICON) : null;


                                                double windSpeed = ((weatherObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_SPEED)))?
                                                        weatherObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_SPEED) : 0.0;

                                                double windDegree = ((weatherObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_DEGREE)))?
                                                        weatherObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_DEGREE) : 0.0;

                                                int cloudChancePercentage = ((weatherObj.has(JsonWeatherResponseKeys.JSON_INT_CLOUDS)))?
                                                        weatherObj.getInt(JsonWeatherResponseKeys.JSON_INT_CLOUDS) : 0;

                                                if (weatherMain == null || weatherIcon == null){
                                                    continue;
                                                }

                                                WeatherObject weatherObject = new WeatherObject(timeStamp, currentTemp, minTemp, maxTemp);
                                                weatherObject.setNightTemperature(nightTemp);
                                                weatherObject.setEveningTemperature(eveTemp);
                                                weatherObject.setMorningTemperature(mornTemp);
                                                weatherObject.setAtmosphericPressure(pressure);
                                                weatherObject.setHumidityPercentage(humidity);
                                                weatherObject.setWeatherId(weatherId);
                                                weatherObject.setWeatherMain(weatherMain);
                                                weatherObject.setWeatherDescription(weatherDescription);
                                                weatherObject.setWeatherIcon(weatherIcon);
                                                weatherObject.setWindSpeed(windSpeed);
                                                weatherObject.setWindDirectionDegrees(windDegree);
                                                weatherObject.setCloudsPercentage(cloudChancePercentage);
                                                weatherObjects.add(weatherObject);
                                            }
                                        }
                                    }

                                    if (weatherObjects == null || weatherObjects.isEmpty() || cityObject == null){
                                        listener.onError(new RequestError("Return Values Null",
                                                ERROR_UNABLE_TO_GET_WEATHER));
                                        return;
                                    }

                                    listener.onForecast(weatherObjects, cityObject, daysCount);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    listener.onError(new RequestError(e.getMessage(),
                                            ERROR_UNABLE_TO_GET_WEATHER));
                                }
                            }else {
                                listener.onError(new RequestError("Object doesn't have \"code\"",
                                        ERROR_UNABLE_TO_GET_WEATHER));
                            }
                        }else {
                            listener.onError(new RequestError("Json Object Null",
                                    ERROR_UNABLE_TO_GET_WEATHER));
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(new RequestError(error.getMessage(), ERROR_UNABLE_TO_GET_WEATHER));
                    }
                });


                JsonObjectRequest requestCurrent = new JsonObjectRequest(Request.Method.GET,
                        requestBuilder.getCurrentUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Get Data
                        if (response != null){
                            Log.d(TAG, "Current Response: "+response.toString());
                            if (response.has(JsonWeatherResponseKeys.JSON_INT_CODE)){
                                try {
                                    int code = response.getInt(JsonWeatherResponseKeys.JSON_INT_CODE);
                                    if (code != CODE_SUCCESS){
                                        listener.onError(new RequestError("Response code was not successful: "
                                                + String.valueOf(code), ERROR_UNABLE_TO_GET_WEATHER));
                                        return;
                                    }
                                    //Initialize Return Objects
                                    WeatherObject weatherObject = null;
                                    CityObject cityObject = null;

                                    double cityLatitude = 0.0, cityLongitude = 0.0;

                                    int cityId = ((response.has(JsonWeatherResponseKeys.JSON_INT_CITY_ID)))?
                                            response.getInt(JsonWeatherResponseKeys.JSON_INT_CITY_ID) : 0;

                                    String cityName = ((response.has(JsonWeatherResponseKeys.JSON_STRING_CITY_NAME)))?
                                            response.getString(JsonWeatherResponseKeys.JSON_STRING_CITY_NAME) : null;

                                    JSONObject coordObj = ((response.has(JsonWeatherResponseKeys.JSON_OBJ_CITY_COORDINATES)))?
                                            response.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_CITY_COORDINATES) : null;

                                    if (coordObj != null){
                                        cityLatitude = ((coordObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LATITUDE)))?
                                                coordObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LATITUDE) : 0.0;

                                        cityLongitude = ((coordObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LONGITUDE)))?
                                                coordObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_COORDINATES_LONGITUDE) : 0.0;
                                    }

                                    //Create City Object
                                    cityObject = new CityObject(cityName, "");
                                    cityObject.setId(cityId);
                                    cityObject.setLatitude(cityLatitude);
                                    cityObject.setLongitude(cityLongitude);


                                    long timeStamp = ((response.has(JsonWeatherResponseKeys.JSON_LONG_DATETIME)))?
                                            response.getLong(JsonWeatherResponseKeys.JSON_LONG_DATETIME) : 0;

                                    JSONObject mainObj = ((response.has(JsonWeatherResponseKeys.JSON_OBJ_MAIN)))?
                                            response.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_MAIN) : null;

                                    if (mainObj == null){
                                        return;
                                    }

                                    double currentTemp = ((mainObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_TEMP)))?
                                            mainObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_TEMP) : 0.0;

                                    double minTemp = ((mainObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_TEMP_MIN)))?
                                            mainObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_TEMP_MIN) : 0.0;

                                    double maxTemp = ((mainObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_TEMP_MAX)))?
                                            mainObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_TEMP_MAX) : 0.0;

                                    double pressure = ((mainObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_PRESSURE)))?
                                            mainObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_PRESSURE) : 0.0;

                                    double humidity = ((mainObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_HUMIDITY)))?
                                            mainObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_TEMP_HUMIDITY) : 0.0;

                                    JSONArray weatherArray = ((response.has(JsonWeatherResponseKeys.JSON_ARRAY_WEATHER)))?
                                            response.getJSONArray(JsonWeatherResponseKeys.JSON_ARRAY_WEATHER) : null;

                                    if (weatherArray == null || weatherArray.length() == 0){
                                        return;
                                    }

                                    // Get the first object in array
                                    JSONObject weatherDetailsObj = weatherArray.getJSONObject(0);

                                    if (weatherDetailsObj == null){
                                        return;
                                    }

                                    int weatherId = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_INT_WEATHER_ID)))?
                                            weatherDetailsObj.getInt(JsonWeatherResponseKeys.JSON_INT_WEATHER_ID) : 0;

                                    String weatherMain = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_STRING_WEATHER_MAIN)))?
                                            weatherDetailsObj.getString(JsonWeatherResponseKeys.JSON_STRING_WEATHER_MAIN) : null;

                                    String weatherDescription = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_STRING_WEATHER_DESCRIPTION)))?
                                            weatherDetailsObj.getString(JsonWeatherResponseKeys.JSON_STRING_WEATHER_DESCRIPTION) : "";

                                    String weatherIcon = ((weatherDetailsObj.has(JsonWeatherResponseKeys.JSON_STRING_WEATHER_ICON)))?
                                            weatherDetailsObj.getString(JsonWeatherResponseKeys.JSON_STRING_WEATHER_ICON) : null;

                                    //Wind Object
                                    JSONObject windObj = ((response.has(JsonWeatherResponseKeys.JSON_OBJ_WIND)))?
                                            response.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_WIND) : null;

                                    double windSpeed = ((windObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_SPEED)))?
                                            windObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_SPEED) : 0.0;

                                    double windDegree = ((windObj.has(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_DEGREE)))?
                                            windObj.getDouble(JsonWeatherResponseKeys.JSON_DOUBLE_WIND_DEGREE) : 0.0;

                                    //Clouds Object
                                    JSONObject cloudsObj = ((response.has(JsonWeatherResponseKeys.JSON_OBJ_CLOUD)))?
                                            response.getJSONObject(JsonWeatherResponseKeys.JSON_OBJ_CLOUD) : null;

                                    int cloudChancePercentage = ((cloudsObj.has(JsonWeatherResponseKeys.JSON_INT_CLOUDS_ALL)))?
                                            cloudsObj.getInt(JsonWeatherResponseKeys.JSON_INT_CLOUDS_ALL) : 0;

                                    if (weatherMain == null || weatherIcon == null){
                                        return;
                                    }

                                    weatherObject = new WeatherObject(timeStamp, currentTemp, minTemp, maxTemp);
                                    weatherObject.setAtmosphericPressure(pressure);
                                    weatherObject.setHumidityPercentage(humidity);
                                    weatherObject.setWeatherId(weatherId);
                                    weatherObject.setWeatherMain(weatherMain);
                                    weatherObject.setWeatherDescription(weatherDescription);
                                    weatherObject.setWeatherIcon(weatherIcon);
                                    weatherObject.setWindSpeed(windSpeed);
                                    weatherObject.setWindDirectionDegrees(windDegree);
                                    weatherObject.setCloudsPercentage(cloudChancePercentage);

                                    if (weatherObject == null || cityObject == null){
                                        listener.onError(new RequestError("Return Values Null",
                                                ERROR_UNABLE_TO_GET_WEATHER));
                                        return;
                                    }

                                    listener.onCurrentWeather(weatherObject, cityObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    listener.onError(new RequestError(e.getMessage(),
                                            ERROR_UNABLE_TO_GET_WEATHER));
                                }
                            }else {
                                listener.onError(new RequestError("Object doesn't have \"code\"",
                                        ERROR_UNABLE_TO_GET_WEATHER));
                            }
                        }else {
                            listener.onError(new RequestError("Json Object Null",
                                    ERROR_UNABLE_TO_GET_WEATHER));
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(new RequestError(error.getMessage(), ERROR_UNABLE_TO_GET_WEATHER));
                    }
                });

                requestCurrent.setShouldCache(false);
                requestForecast.setShouldCache(false);

                WeatherRequestQueue.getInstance(context).getQueue().add(requestCurrent);
                WeatherRequestQueue.getInstance(context).getQueue().add(requestForecast);
            }
        };
        //If Async Start New Thread and Run
        if (asynchronous){
            Runnable rAsync = new Runnable() {
                @Override
                public void run() {
                    new Thread(threadGroup, new Runnable() {
                        @Override
                        public void run() {
                            r.run();
                        }
                    }).start();
                }
            };
            return rAsync;
        }
        return r;
    }
}
