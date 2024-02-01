package com.shv.canifly.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.shv.canifly.R
import com.shv.canifly.databinding.FragmentConditionsBinding
import com.shv.canifly.presentation.viewmodels.WeatherConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale


@AndroidEntryPoint
class ConditionsFragment : Fragment() {

    private var _binding: FragmentConditionsBinding? = null
    private val binding: FragmentConditionsBinding
        get() = _binding ?: throw RuntimeException("FragmentConditionsBinding is null")

    private val viewModel: WeatherConditionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        Log.d("ConditionsFragment", viewModel.toString())
        viewModel.loadWeatherInfo()
        observeViewModels()
    }

    private fun observeViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding) {
                    viewModel.state.collect {
                        if (it.isLoading) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                        it.error?.let { errorMsg ->
                            Toast.makeText(
                                context,
                                errorMsg,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        it.weatherInfo?.let { wI ->
                            wI.currentWeatherData?.let { hourlyWeather ->
                                progressBar.visibility = View.GONE
                                tvTemperature.text = "${hourlyWeather.temperature2m} ${wI.units.temperature}"
                                tvCurrentTime.text = hourlyWeather.time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
                                ivWeatherNow.setImageResource(hourlyWeather.weatherType.iconRes)
                                tvWindSpeed.text = "${hourlyWeather.windSpeed10m} ${wI.units.windSpeed}"
                                tvWindGusts.text = "${hourlyWeather.windGusts10m} ${wI.units.windSpeed}"
                                tvWindDir.text = "${hourlyWeather.windDirection10m} ${wI.units.windDirection}"
                                tvPrecipProb.text = "${hourlyWeather.precipitationProbability} ${wI.units.precipitationProbability}"
                                tvCloudCover.text = "${hourlyWeather.cloudCover} ${wI.units.cloudCover}"
                                tvVisibility.text = "${hourlyWeather.visibility} km"
                            }
                            wI.currentDailyWeatherData?.let {dailyWeather ->
                                tvMinMaxTemperature.text = "High: ${dailyWeather.temperatureMax}  ${wI.units.temperature} / Low: ${dailyWeather.temperatureMin}  ${wI.units.temperature}"
                                tvSunrise.text = dailyWeather.sunrise.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
                                tvSunset.text = dailyWeather.sunset.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}