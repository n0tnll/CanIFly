package com.shv.canifly.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textview.MaterialTextView
import com.shv.canifly.R
import com.shv.canifly.data.extensions.shortDayOfWeek
import com.shv.canifly.data.extensions.toTimeString
import com.shv.canifly.databinding.FragmentConditionsBinding
import com.shv.canifly.domain.entity.DailyWeather
import com.shv.canifly.domain.entity.HourlyWeather
import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.entity.WeatherUnits
import com.shv.canifly.presentation.viewmodels.WeatherConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

@AndroidEntryPoint
class ConditionsFragment : Fragment() {

    private var _binding: FragmentConditionsBinding? = null
    private val binding: FragmentConditionsBinding
        get() = _binding ?: throw RuntimeException("FragmentConditionsBinding is null")

    private val viewModel: WeatherConditionsViewModel by activityViewModels()

    private val daysList: MutableList<MaterialTextView> = mutableListOf()

    private var chosenDay: Int = DEFAULT_CHOSEN_DAY
    private var chosenHour: Int = LocalTime.now().hour

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConditionsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
        addTvToList()
        setOnDayClickListeners()
    }

    private fun observeViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding) {
                    viewModel.state.collect {
                        progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE

                        it.error?.let { errorMsg ->
                            Toast.makeText(
                                context,
                                errorMsg,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        setCurrentWeatherConditions(it.weatherInfo)
                        binding.sliderTime.value = chosenHour.toFloat()
                        binding.sliderTime.addOnChangeListener { _, value, _ ->
                            chosenHour = value.toInt()
                            setCurrentWeatherConditions(it.weatherInfo)
                        }
                        setDaysTab(it.weatherInfo?.weatherDailyData)
                    }
                }
            }
        }
    }

    private fun setOnDayClickListeners() {
        for ((index, days) in daysList.withIndex()) {
            days.setOnClickListener {
                chosenDay = index
                setCurrentWeatherConditions(viewModel.state.value.weatherInfo)
            }
        }
    }

    private fun setCurrentWeatherConditions(
        weatherInfo: WeatherInfo?
    ) {
        var hourlyConditions: HourlyWeather? = null
        var dailyConditions: DailyWeather? = null
        weatherInfo?.let { wI ->
            if (chosenDay == DEFAULT_CHOSEN_DAY) {
                hourlyConditions = wI.weatherPerDayData[chosenDay]?.get(chosenHour)
                dailyConditions = wI.currentDailyWeatherData
            } else {
                dailyConditions = wI.weatherDailyData?.get(chosenDay)
                hourlyConditions = wI.weatherPerDayData[chosenDay]?.get(chosenHour)
            }
            for ((index, tv) in daysList.withIndex()) {
                if (index == chosenDay)
                    tv.setBackgroundResource(R.drawable.select_day_background)
                else
                    tv.setBackgroundResource(R.drawable.unselect_day_background)
            }
            setCurrentHourlyCondition(hourlyConditions, wI.units)
            setCurrentDailyCondition(dailyConditions, wI.units)
        }
    }

    private fun setCurrentHourlyCondition(hourlyWeather: HourlyWeather?, units: WeatherUnits) {
        hourlyWeather?.let {
            with(binding) {
                progressBar.visibility = View.GONE
                tvTemperature.text = getStringValueWithUnits(
                    it.temperature2m,
                    units.temperature
                )
                tvLastWeatherUpdate.text = String.format(
                    requireContext().getString(R.string.last_update),
                    LocalDateTime.now().toTimeString()
                )
                tvCurrentTime.text = hourlyWeather.time.toTimeString()

                if (it.isDay == 0)
                    ivWeatherNow.setImageResource(it.weatherType.iconResNight)
                else
                    ivWeatherNow.setImageResource(it.weatherType.iconResDay)

                tvWindSpeed.text = getStringValueWithUnits(
                    it.windSpeed10m,
                    units.windSpeed
                )
                tvWindGusts.text = getStringValueWithUnits(
                    it.windGusts10m,
                    units.windSpeed
                )
                tvWindDir.text = getStringValueWithUnits(
                    it.windDirection10m,
                    units.windDirection
                )
                tvPrecipProb.text = getStringValueWithUnits(
                    it.precipitationProbability,
                    units.precipitationProbability
                )
                tvCloudCover.text = getStringValueWithUnits(
                    it.cloudCover,
                    units.cloudCover
                )
                tvVisibility.text = getStringValueWithUnits(
                    it.visibility,
                    "km"
                )
            }
        }
    }

    private fun setCurrentDailyCondition(dailyWeather: DailyWeather?, units: WeatherUnits) {
        dailyWeather?.let {
            with(binding) {
                tvMinMaxTemperature.text = String.format(
                    requireContext().getString(R.string.high_low_temp),
                    getStringValueWithUnits(
                        it.temperatureMax,
                        units.temperature
                    ),
                    getStringValueWithUnits(
                        it.temperatureMin,
                        units.temperature
                    )
                )
                tvSunrise.text = dailyWeather.sunrise.toTimeString()
                tvSunset.text = dailyWeather.sunset.toTimeString()
            }
        }
    }

    private fun setDaysTab(dailyWeather: List<DailyWeather>?) {
        dailyWeather?.let {
            for ((index, days) in it.withIndex()) {
                daysList[index].text = days.day.shortDayOfWeek()
            }
            daysList[chosenDay].setBackgroundResource(R.drawable.select_day_background)
        }
    }

    private fun addTvToList() {
        with(binding) {
            daysList.addAll(
                listOf(
                    tvDayOfWeek1,
                    tvDayOfWeek2,
                    tvDayOfWeek3,
                    tvDayOfWeek4,
                    tvDayOfWeek5,
                    tvDayOfWeek6,
                    tvDayOfWeek7
                )
            )
        }
    }

    private fun <T> getStringValueWithUnits(
        value: T,
        measure: String
    ): String {
        return String.format(
            requireContext().getString(R.string.value_with_units),
            value,
            measure
        )
    }

    private fun clearList() {
        daysList.forEach {
            it.setBackgroundResource(R.drawable.unselect_day_background)
        }
        daysList.clear()
    }

    override fun onStop() {
        super.onStop()
        clearList()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val DEFAULT_CHOSEN_DAY = 0
    }
}