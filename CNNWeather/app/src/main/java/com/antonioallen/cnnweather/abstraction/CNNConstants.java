package com.antonioallen.cnnweather.abstraction;

/**
 * Created by antonioallen on 11/3/16.
 */

public interface CNNConstants {

    String WEATHER_API_KEY = "e053708e57d5b6067cfde34ccd1667e8";
    String ERROR_UNABLE_TO_GET_WEATHER = "Unable to get weather. Check connection";
    String INTENT_EXTRA_WEATHER_OBJ = "INTENT_WEATHER";
    double MPH_TO_KPH_CONVERSION_RATE = 1.60934;
    int DATE_TIME_CONVERSION = 1000;
    int DEFAULT_CACHE_SIZE = 1024;
    int CODE_SUCCESS = 200;
    String WEATHER_DIRECTIONS[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};


    class ImageArtworkCodes{
        final static String _01 = "01";
        final static String _02 = "02";
        final static String _03 = "03";
        final static String _04 = "04";
        final static String _09 = "09";
        final static String _10 = "10";
        final static String _11 = "11";
        final static String _13 = "13";
        final static String _50 = "50";
    }

    class JsonWeatherResponseKeys{
        public static String JSON_OBJ_CITY = "city";
        public static String JSON_INT_CITY_ID = "id";
        public static String JSON_STRING_CITY_NAME = "name";
        public static String JSON_OBJ_CITY_COORDINATES = "coord";
        public static String JSON_DOUBLE_COORDINATES_LONGITUDE = "lon";
        public static String JSON_DOUBLE_COORDINATES_LATITUDE = "lat";
        public static String JSON_STRING_CITY_COUNTRY = "country";
        public static String JSON_INT_CITY_POPULATION = "population";
        public static String JSON_INT_CODE = "cod";
        public static String JSON_DOUBLE_MESSAGE = "message";
        public static String JSON_INT_COUNT = "cnt";
        public static String JSON_ARRAY_LIST = "list";
        public static String JSON_LONG_DATETIME = "dt";
        public static String JSON_OBJ_TEMP = "temp";
        public static String JSON_OBJ_MAIN = "main";
        public static String JSON_OBJ_WIND = "wind";
        public static String JSON_OBJ_CLOUD = "clouds";
        public static String JSON_DOUBLE_TEMP_TEMP = "temp";
        public static String JSON_DOUBLE_TEMP_PRESSURE = "pressure";
        public static String JSON_DOUBLE_TEMP_HUMIDITY = "humidity";
        public static String JSON_DOUBLE_TEMP_TEMP_MIN = "temp_min";
        public static String JSON_DOUBLE_TEMP_TEMP_MAX = "temp_max";
        public static String JSON_DOUBLE_TEMP_DAY = "day";
        public static String JSON_DOUBLE_TEMP_MIN = "min";
        public static String JSON_DOUBLE_TEMP_MAX = "max";
        public static String JSON_DOUBLE_TEMP_NIGHT = "night";
        public static String JSON_DOUBLE_TEMP_EVENING = "eve";
        public static String JSON_DOUBLE_TEMP_MORNING = "morn";
        public static String JSON_DOUBLE_PRESSURE = "pressure";
        public static String JSON_INT_HUMIDITY = "humidity";
        public static String JSON_ARRAY_WEATHER = "weather";
        public static String JSON_INT_WEATHER_ID = "id";
        public static String JSON_STRING_WEATHER_MAIN = "main";
        public static String JSON_STRING_WEATHER_DESCRIPTION = "description";
        public static String JSON_STRING_WEATHER_ICON = "icon";
        public static String JSON_DOUBLE_WIND_SPEED = "speed";
        public static String JSON_DOUBLE_WIND_DEGREE = "deg";
        public static String JSON_INT_CLOUDS = "clouds";
        public static String JSON_INT_CLOUDS_ALL = "all";
    }
}
