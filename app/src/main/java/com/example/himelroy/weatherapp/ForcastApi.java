package com.example.himelroy.weatherapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mobile App Develop on 5/9/2018.
 */

public interface ForcastApi {

    @GET
    Call<ForecastWeatherResponse> getForcastWeatherResponse(@Url String urlString);
}
