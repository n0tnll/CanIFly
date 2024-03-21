package com.shv.canifly.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shv.canifly.R
import com.shv.canifly.data.extensions.toDateString
import com.shv.canifly.data.extensions.toTimeString
import com.shv.canifly.databinding.WatchingDayItemBinding
import com.shv.canifly.domain.entity.WatchingDate
import kotlin.math.roundToInt

class WatchingDateViewHolder(
    private val binding: WatchingDayItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(watchingDate: WatchingDate, context: Context) {
        with(binding) {
            with(watchingDate) {
                tvLocation.text = "Vladivotok $location"
                tvDate.text = dateTime.toDateString()
                tvTime.text = dateTime.toTimeString()
                tvTemperature.text = String.format(
                    context.getString(R.string.value_temp),
                    conditions.temperature2m.roundToInt()
                )
                tvWindSpeed.text = String.format(
                    context.getString(R.string.wind_value),
                    conditions.windSpeed10m.roundToInt()
                )
                tvWindGusts.text = String.format(
                    context.getString(R.string.gusts_value),
                    conditions.windGusts10m.roundToInt()
                )
                tvFeelsLike.text = String.format(
                    context.getString(R.string.feels_like),
                    conditions.feelsLikeTemp.roundToInt()
                )
                ivWeatherNow.setImageResource(conditions.weatherType.iconResDay)
                //TODO: fly status later
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): WatchingDateViewHolder {
            val binding = WatchingDayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return WatchingDateViewHolder(binding)
        }
    }
}