package com.example.himelroy.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by Mobile App Develop on 5/7/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
private List<ForecastWeatherResponse.ForecastList> forecastWeathers;
private Context context;

    public CustomAdapter(List<ForecastWeatherResponse.ForecastList>forecastWeatherList, Context context) {
        this.forecastWeathers = forecastWeatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.werather_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    holder.showMinForecast.setText(forecastWeathers.get(0).getHumidity());
    holder.showDayName.setText(String.valueOf(forecastWeathers.get(0).getTemp().getDay()));
    holder.showDateForecast.setText(forecastWeathers.get(0).getWeather().get(0).getDescription());


    }

    @Override
    public int getItemCount() {
        return forecastWeathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView showMaxForecast, showMinForecast,showDateForecast, showDayName;
        ImageView showImageCloud;
        public ViewHolder(View itemView) {
            super(itemView);
            showMaxForecast = itemView.findViewById(R.id.maxTextView);
            showMinForecast = itemView.findViewById(R.id.showMinTextView);
            showDateForecast = itemView.findViewById(R.id.dateShow);
            showDayName = itemView.findViewById(R.id.dayTextView);
            showImageCloud = itemView.findViewById(R.id.imageView);

        }
    }
}
