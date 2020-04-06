package com.example.himelroy.weatherapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private double latitude;
    private double longitude;
    private ForcastApi forcastApi;
    private   GoogleApiClient googleApiClient;
    private    LocationRequest locationRequest;
    private String units = "metric";
    private String cityName = "Dhaka";
    private RecyclerView recyclerView;
    private ForecastWeatherResponse forecastWeatherResponse;
    private ForecastWeatherAdapter forecastWeatherAdapter;
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/";


    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View forecast = inflater.inflate(R.layout.fragment_forecast, container, false);
            recyclerView = forecast.findViewById(R.id.recyclerViewWeather);
//        forecastWeatherAdapter = new ForecastWeatherAdapter(getActivity(),forecastWeatherResponse.getList());
//
//        LinearLayoutManager lm = new LinearLayoutManager(getContext());
//        lm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(lm);
//        recyclerView.setAdapter(forecastWeatherAdapter);


        googleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        checkLocationPermission();

        return forecast;
    }



    private void getCurrentWeatherData(final String cityName) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        forcastApi = retrofit.create(ForcastApi.class);

        String urlString = String.format("daily?q=%s&units=%s&cnt=7&appid=%s",cityName,units,getString(R.string.forcas_api_key));
        Call<ForecastWeatherResponse> responseCall = forcastApi.getForcastWeatherResponse(urlString);
        responseCall.enqueue(new Callback<ForecastWeatherResponse>() {
            @Override
            public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                ForecastWeatherResponse forecastWeatherResponse = response.body();
                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Not ok", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void checkLocationPermission(){
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
            return;
        }
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onPause() {
        googleApiClient.disconnect();
        super.onPause();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create()
                .setInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        checkLocationPermission();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,locationRequest,this
        );

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude= location.getLatitude();
        longitude = location.getLongitude();
        cityName = getCity();
        getCurrentWeatherData(cityName);

    }
    public String getCity() {
        String curCity= "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0) {
                curCity = addressList.get(0).getLocality();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curCity;
    }
}








