package com.shv.canifly.presentation.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shv.canifly.domain.entity.DailyWeather

class DailyWeatherAdapter :
    ListAdapter<DailyWeather, DailyForecastViewHolder>(DailyWeatherDiffUtil()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        context = parent.context
        return DailyForecastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val forecast = getItem(position)
        holder.bind(forecast, context)
    }
}