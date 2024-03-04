package com.shv.canifly.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shv.canifly.domain.entity.HourlyWeather

class DetailedWeatherAdapter :
    ListAdapter<HourlyWeather, DetailedForecastViewHolder>(HourlyWeatherDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailedForecastViewHolder {
        return DetailedForecastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailedForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)
    }
}