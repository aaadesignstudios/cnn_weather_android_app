package com.antonioallen.cnnweather.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by antonioallen on 11/3/16.
 */

public class WeatherObject implements Parcelable {
    //Time of data forecasted
    private long timeStamp;
    //Temperature Values
    private double currentTemperature;
    private double lowTemperature;
    private double highTemperature;
    private double nightTemperature;
    private double eveningTemperature;
    private double morningTemperature;
    private double atmosphericPressure;

    //Weather Humidity
    private double humidityPercentage;

    //Weather
    private int weatherId;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;

    //Wind Data
    private double windSpeed;
    private double windDirectionDegrees;
    private double cloudsPercentage;

    public WeatherObject(){
        //Empty Initializer
    }

    public WeatherObject(long timeStamp, double currentTemperature,
                         double lowTemperature, double highTemperature){
        this.timeStamp = timeStamp;
        this.currentTemperature = currentTemperature;
        this.lowTemperature = lowTemperature;
        this.highTemperature = highTemperature;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(double lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public double getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(double highTemperature) {
        this.highTemperature = highTemperature;
    }

    public double getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public double getEveningTemperature() {
        return eveningTemperature;
    }

    public void setEveningTemperature(double eveningTemperature) {
        this.eveningTemperature = eveningTemperature;
    }

    public double getMorningTemperature() {
        return morningTemperature;
    }

    public void setMorningTemperature(double morningTemperature) {
        this.morningTemperature = morningTemperature;
    }

    public double getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(double atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }

    public double getHumidityPercentage() {
        return humidityPercentage;
    }

    public void setHumidityPercentage(double humidityPercentage) {
        this.humidityPercentage = humidityPercentage;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirectionDegrees() {
        return windDirectionDegrees;
    }

    public void setWindDirectionDegrees(double windDirectionDegrees) {
        this.windDirectionDegrees = windDirectionDegrees;
    }

    public double getCloudsPercentage() {
        return cloudsPercentage;
    }

    public void setCloudsPercentage(double cloudsPercentage) {
        this.cloudsPercentage = cloudsPercentage;
    }

    protected WeatherObject(Parcel in) {
        timeStamp = in.readLong();
        currentTemperature = in.readDouble();
        lowTemperature = in.readDouble();
        highTemperature = in.readDouble();
        nightTemperature = in.readDouble();
        eveningTemperature = in.readDouble();
        morningTemperature = in.readDouble();
        atmosphericPressure = in.readDouble();
        humidityPercentage = in.readDouble();
        weatherId = in.readInt();
        weatherMain = in.readString();
        weatherDescription = in.readString();
        weatherIcon = in.readString();
        windSpeed = in.readDouble();
        windDirectionDegrees = in.readDouble();
        cloudsPercentage = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(timeStamp);
        dest.writeDouble(currentTemperature);
        dest.writeDouble(lowTemperature);
        dest.writeDouble(highTemperature);
        dest.writeDouble(nightTemperature);
        dest.writeDouble(eveningTemperature);
        dest.writeDouble(morningTemperature);
        dest.writeDouble(atmosphericPressure);
        dest.writeDouble(humidityPercentage);
        dest.writeInt(weatherId);
        dest.writeString(weatherMain);
        dest.writeString(weatherDescription);
        dest.writeString(weatherIcon);
        dest.writeDouble(windSpeed);
        dest.writeDouble(windDirectionDegrees);
        dest.writeDouble(cloudsPercentage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WeatherObject> CREATOR = new Parcelable.Creator<WeatherObject>() {
        @Override
        public WeatherObject createFromParcel(Parcel in) {
            return new WeatherObject(in);
        }

        @Override
        public WeatherObject[] newArray(int size) {
            return new WeatherObject[size];
        }
    };
}