package com.shv.canifly.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.shv.canifly.domain.entity.HourlyWeather

class HourlyWeatherDiffUtil : DiffUtil.ItemCallback<HourlyWeather>() {
    override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
        return newItem.time == oldItem.time
    }

    override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
        return newItem == oldItem
    }

}