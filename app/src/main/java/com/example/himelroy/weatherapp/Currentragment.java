package com.example.himelroy.weatherapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Currentragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private FusedLocationProviderClient client;
   private double latitude;
    private double longitude;
    private WeatherApi weatherApi;
    private   GoogleApiClient googleApiClient;
    private    LocationRequest locationRequest;
    private String units = "metric";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
private View currentFragment;
private ImageView imageViewCloud,imageMaxMin,imagesunriseSet,imageHimit;
TextView showTempature,showDate,showDay,showCity,showMin,showMax,showSunrise,showSunset,showHiminidity,showPressere,descriptionTV;
    public Currentragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentFragment = inflater.inflate(R.layout.fragment_currentragment, container, false);

        imageViewCloud = currentFragment.findViewById(R.id.clouedImage);
        imageMaxMin = currentFragment.findViewById(R.id.clouedImage);
        imagesunriseSet = currentFragment.findViewById(R.id.clouedImage);
        imageHimit = currentFragment.findViewById(R.id.clouedImage);

        showTempature = currentFragment.findViewById(R.id.degree);
        showDate = currentFragment.findViewById(R.id.date);
        showDay = currentFragment.findViewById(R.id.days);
        descriptionTV = currentFragment.findViewById(R.id.description);
        showCity = currentFragment.findViewById(R.id.cityName);
        showMin = currentFragment.findViewById(R.id.minTemp);
        showMax = currentFragment.findViewById(R.id.maxTemp);
        showSunrise = currentFragment.findViewById(R.id.sunriseTime);
        showSunset = currentFragment.findViewById(R.id.sunsetTime);
        showHiminidity = currentFragment.findViewById(R.id.huminidtyPercent);
        showPressere = currentFragment.findViewById(R.id.presserPascal);
       // client = LocationServices.getFusedLocationProviderClient(this.getActivity());
         googleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        checkLocationPermission();

        return currentFragment;
    }



    private void getCurrentWeatherData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherApi = retrofit.create(WeatherApi.class);

        String urlString = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s",latitude,longitude,units,getString(R.string.weather_api_key));
        Call<CurrentWeatherResponse> responseCall = weatherApi.getCurrentWeatherData(urlString);
        responseCall.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.code() == 200) {
                    CurrentWeatherResponse currentWeatherResponse = response.body();
//                    Toast.makeText(getActivity(), "this is ok", Toast.LENGTH_SHORT).show();
                    showTempature.setText(String.valueOf(currentWeatherResponse.getMain().getTemp()+"°C"));

                   showCity.setText(currentWeatherResponse.getName()+" , " +currentWeatherResponse.getSys().getCountry());
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    String todayDate = new SimpleDateFormat("MMMM dd, yyyy").format(date);
                    showDate.setText(todayDate);
                    DateFormat df = new SimpleDateFormat("EEEE");
                    String day = df.format(Calendar.getInstance().getTime());
                    showDay.setText(day);
                    showHiminidity.setText(currentWeatherResponse.getMain().getHumidity()+"%");
                    showPressere.setText(currentWeatherResponse.getMain().getPressure()+" hPa");
                    showMin.setText(currentWeatherResponse.getMain().getTempMin()+"°C");
                    showMax.setText(currentWeatherResponse.getMain().getTempMax()+"°C");

                    long sunrise = currentWeatherResponse.getSys().getSunrise();
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(sunrise*1000);
                    String rise = new SimpleDateFormat("h:mm:a").format(Calendar.getInstance().getTime());
                    showSunrise.setText(String.valueOf(rise));

//                    long sunset = currentWeatherResponse.getSys().getSunset();
//                    c.setTimeInMillis(sunset * 1000);
//                    String sunsets = new SimpleDateFormat("h:mm").format(date);
//                    showSunset.setText(String.valueOf(sunsets));
                    DateFormat ds = DateFormat.getTimeInstance();
                    String sunrises = ds.format(new Date(currentWeatherResponse.getSys().getSunset()));
                    showSunset.setText(String.valueOf(sunrises));
                    descriptionTV.setText(String.valueOf(currentWeatherResponse.getWeather().get(0).getDescription()));
                    Picasso.get().load("https://ssl.gstatic.com/onebox/weather/64/cloudy.png").into(imageViewCloud);


                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {


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
        getCurrentWeatherData();

    }
}
