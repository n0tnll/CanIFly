package com.shv.canifly.presentation.adapters

import android.animation.LayoutTransition
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.shv.canifly.R
import com.shv.canifly.data.extensions.detailedDayString
import com.shv.canifly.data.extensions.getDayName
import com.shv.canifly.data.extensions.toNormalDayName
import com.shv.canifly.data.extensions.toTimeString
import com.shv.canifly.databinding.DailyWeatherItemBinding
import com.shv.canifly.databinding.DetailedForecastItemBinding
import com.shv.canifly.domain.entity.DailyWeather
import com.shv.canifly.domain.entity.HourlyWeather
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt

class DailyForecastViewHolder(
    private val binding: DailyWeatherItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private fun getCurrentHour() = if (LocalTime.now().minute < 30)
        LocalTime.now().hour
    else
        LocalTime.now().hour + 1

    private val detailedAdapter by lazy {
        DetailedWeatherAdapter()
    }

    fun bind(dailyWeather: DailyWeather, context: Context) {
        binding.expandableInfo.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        bindDailyHeader(dailyWeather, context)
    }

    private fun bindDailyHeader(dailyWeather: DailyWeather, context: Context) {
        with(binding) {
            with(dailyWeather) {
                tvDay.text = day.detailedDayString()
                tvSunrise.text = String.format(
                    context.getString(R.string.sunrise),
                    sunrise.toTimeString()
                )
                tvSunset.text = String.format(
                    context.getString(R.string.sunset),
                    sunset.toTimeString()
                )
                val currentDay = day.dayOfWeek.toNormalDayName()
                tvCurrentDay.text = getDayName(currentDay, context)
                tvPrecipProb.text = if (precipProbMax > 0) String.format(
                    context.getString(R.string.precib_prob_percent),
                    precipProbMax
                ) else ""
                ivWeatherNow.setImageResource(weatherType.iconResDay)
                tvTemperatures.text = String.format(
                    context.getString(R.string.high_low_temp_short),
                    temperatureMax.roundToInt(),
                    temperatureMin.roundToInt()
                )
            }
            bindDetailedForecast(dailyWeather, context)
        }
    }

    private fun bindDetailedForecast(dailyWeather: DailyWeather, context: Context) {
        with(binding) {
            with(dailyWeather) {
                rvDetailedForecast.adapter = detailedAdapter
                root.setOnClickListener {
                    expandableInfo.visibility =
                        if (expandableInfo.isVisible) View.GONE else View.VISIBLE
                    toggleButton.check(btnConditions.id)
                    detailedAdapter.submitList(filterFromCurrentHourForecast(dailyWeather))
                }
                toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        if (checkedId == btnConditions.id) {
                            conditionsLayout.visibility = View.VISIBLE
                            windProfileLayout.visibility = View.GONE
                        } else {
                            conditionsLayout.visibility = View.GONE
                            windProfileLayout.visibility = View.VISIBLE
                            sliderTime.value = getCurrentHour().toFloat()
                            bindWindProfile(hourlyWeather, context)
                            sliderTime.addOnChangeListener { _, value, _ ->
                                bindWindProfile(hourlyWeather, context, value.toInt())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun filterFromCurrentHourForecast(dailyWeather: DailyWeather) =
        if (dailyWeather.day == LocalDate.now()) {
            dailyWeather.hourlyWeather.filter {
                it.time.hour >= LocalTime.now().hour
            }
        } else dailyWeather.hourlyWeather

    private fun bindWindProfile(
        hourlyWeather: List<HourlyWeather>,
        context: Context,
        hour: Int = getCurrentHour()
    ) {
        with(binding) {
            tvSelectedTime.text = String.format(
                context.getString(R.string.selected_time),
                hourlyWeather[hour].time.toTimeString()
            )

            tvWindSpeed1000.text = getWindSpeedString(context, hourlyWeather[hour].windSpeed1000m)
            tvWindSpeed800.text = getWindSpeedString(context, hourlyWeather[hour].windSpeed800m)
            tvWindSpeed500.text = getWindSpeedString(context, hourlyWeather[hour].windSpeed500m)
            tvWindSpeed300.text = getWindSpeedString(context, hourlyWeather[hour].windSpeed320m)
            tvWindSpeed100.text = getWindSpeedString(context, hourlyWeather[hour].windSpeed120m)
            tvWindSpeed10.text = getWindSpeedString(context, hourlyWeather[hour].windSpeed10m)

            tvTemperature1000.text =
                getTemperatureString(context, hourlyWeather[hour].temperature1000m)
            tvTemperature800.text =
                getTemperatureString(context, hourlyWeather[hour].temperature800m)
            tvTemperature500.text =
                getTemperatureString(context, hourlyWeather[hour].temperature500m)
            tvTemperature300.text =
                getTemperatureString(context, hourlyWeather[hour].temperature320m)
            tvTemperature100.text =
                getTemperatureString(context, hourlyWeather[hour].temperature120m)
            tvTemperature10.text =
                getTemperatureString(context, hourlyWeather[hour].temperature2m)
        }
    }

    private fun getWindSpeedString(context: Context, value: Double) =
        String.format(
            context.getString(R.string.value_wind),
            value.roundToInt()
        )

    private fun getTemperatureString(context: Context, value: Double) =
        String.format(
            context.getString(R.string.value_temp),
            value.roundToInt()
        )

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

class DetailedForecastViewHolder(
    private val binding: DetailedForecastItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: HourlyWeather) {
        with(binding) {
            with(forecast) {
                tvHour.text = time.toTimeString()
                tvWindSpeed.text = windSpeed10m.roundToInt().toString()
                tvWindGusts.text = windGusts10m.roundToInt().toString()
                tvTemperature.text = temperature2m.roundToInt().toString()
                tvPrecipProb.text = precipitationProbability.toString()
                tvCloudCover.text = cloudCover.toString()
                tvVisibility.text = visibility.toString()
                tvGoodToFly.text = "yes"
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): DetailedForecastViewHolder {
            val binding = DetailedForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return DetailedForecastViewHolder(binding)
        }
    }
}

















