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

public class ForecastWeatherAdapter extends RecyclerView.Adapter<ForecastWeatherAdapter.MyViewHolder> {
    private List<ForecastWeatherResponse.ForecastList> forecastWeatherResponse;
    private Context context;

    public ForecastWeatherAdapter( Context context, List<ForecastWeatherResponse.ForecastList> forecastWeatherResponse) {
        this.forecastWeatherResponse = forecastWeatherResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.werather_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.showMaxForecast.setText(String.valueOf(forecastWeatherResponse.get(position).getTemp().getMax())+"C");
       holder.showMinForecast.setText(String.valueOf(forecastWeatherResponse.get(position).getTemp().getMin())+"C");

    }

    @Override
    public int getItemCount() {
        return forecastWeatherResponse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView showMaxForecast, showMinForecast,showDateForecast, showDayName;
        ImageView showImageCloud;
        public MyViewHolder(View itemView) {
            super(itemView);
            showMaxForecast = itemView.findViewById(R.id.maxTextView);
            showMinForecast = itemView.findViewById(R.id.showMinTextView);
            showDateForecast = itemView.findViewById(R.id.dateShow);
            showDayName = itemView.findViewById(R.id.dayTextView);
            showImageCloud = itemView.findViewById(R.id.imageView);
        }
    }
}
