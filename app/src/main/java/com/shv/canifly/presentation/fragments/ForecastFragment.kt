package com.shv.canifly.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shv.canifly.R
import com.shv.canifly.databinding.FragmentForecastBinding
import com.shv.canifly.presentation.adapters.DailyWeatherAdapter
import com.shv.canifly.presentation.adapters.HourlyWeatherAdapter
import com.shv.canifly.presentation.viewmodels.WeatherConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding: FragmentForecastBinding
        get() = _binding ?: throw RuntimeException("FragmentForecastBinding is null")

    private val hourlyAdapter by lazy {
        HourlyWeatherAdapter()
    }
    private val dailyAdapter by lazy {
        DailyWeatherAdapter()
    }
    private val viewModel: WeatherConditionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadWeatherInfo()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding) {
                    viewModel.state.collect { weatherState ->
                        if (weatherState.isLoading) {
                            pbForecast.visibility = View.VISIBLE
                        } else {
                            pbForecast.visibility = View.GONE
                        }
                        weatherState.error?.let { errorMsg ->
                            Toast.makeText(
                                context,
                                errorMsg,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        weatherState.weatherInfo?.weatherPerDayData?.get(0)?.let { hourlyWeatherList->
                            hourlyAdapter.submitList(hourlyWeatherList)
                        }
                        weatherState.weatherInfo?.weatherDailyData?.let {
                            dailyAdapter.submitList(it)
                        }
                        Log.d("weatherDailyData","${weatherState.weatherInfo?.weatherDailyData?.toString()}")
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHourlyWeather.adapter = hourlyAdapter
        binding.rvDailyForecast.adapter = dailyAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}