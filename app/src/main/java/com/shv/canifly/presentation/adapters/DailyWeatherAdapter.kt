package com.shv.canifly.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shv.canifly.domain.entity.DailyWeather
import com.shv.canifly.domain.entity.HourlyWeather

class DailyWeatherAdapter :
    ListAdapter<DailyWeather, DailyForecastViewHolder>(DailyWeatherDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        return DailyForecastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast)
    }
}