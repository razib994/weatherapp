package com.example.himelroy.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mobile App Develop on 5/7/2018.
 */

public interface WeatherApi {
    @GET
    Call<CurrentWeatherResponse> getCurrentWeatherData(@Url String urlString);
}
