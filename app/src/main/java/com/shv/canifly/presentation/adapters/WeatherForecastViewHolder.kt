package com.shv.canifly.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shv.canifly.databinding.DailyWeatherItemBinding
import com.shv.canifly.databinding.HourlyWeatherItemBinding
import com.shv.canifly.domain.entity.DailyWeather
import com.shv.canifly.domain.entity.HourlyWeather
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class HourlyForecastViewHolder(
    private val binding: HourlyWeatherItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: HourlyWeather) {
        with(binding) {
            with(forecast) {
                tvTemperature.text = "${temperature2m} °"
                ivWeatherNow.setImageResource(weatherType.iconRes)
                tvCurrentTime.text =
                    time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): HourlyForecastViewHolder {
            val binding = HourlyWeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HourlyForecastViewHolder(binding)
        }
    }
}

class DailyForecastViewHolder(
    private val binding: DailyWeatherItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: DailyWeather) {
        with(binding) {
            with(forecast) {
                val currentDay = day.dayOfWeek.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                tvCurrentDay.text =
                    if (LocalDate.now().dayOfMonth == day.dayOfMonth) "Today" else currentDay
                if (precipitationProbabilityMax > 0)
                    tvPrecipProb.text = precipitationProbabilityMax.toString() + "%"
                else
                    tvPrecipProb.text = ""
                ivWeatherNow.setImageResource(weatherType.iconRes)
                tvTemperatures.text = "$temperatureMin°/$temperatureMax°"
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): DailyForecastViewHolder {
            val binding = DailyWeatherItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return DailyForecastViewHolder(binding)
        }
    }
}

















