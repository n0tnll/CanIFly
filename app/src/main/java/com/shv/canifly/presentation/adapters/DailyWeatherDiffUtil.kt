package com.shv.canifly.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.shv.canifly.domain.entity.DailyWeather

class DailyWeatherDiffUtil : DiffUtil.ItemCallback<DailyWeather>() {
    override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return newItem.day == oldItem.day
    }

    override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return newItem == oldItem
    }
}