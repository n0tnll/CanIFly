package com.shv.canifly.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shv.canifly.domain.entity.HourlyWeather

class HourlyWeatherAdapter :
    ListAdapter<HourlyWeather, HourlyForecastViewHolder>(HourlyWeatherDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        return HourlyForecastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)
    }
}