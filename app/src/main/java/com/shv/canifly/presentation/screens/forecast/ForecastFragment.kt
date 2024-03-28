package com.shv.canifly.presentation.screens.forecast

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
import com.shv.canifly.databinding.FragmentForecastBinding
import com.shv.canifly.presentation.adapters.DailyWeatherAdapter
import com.shv.canifly.presentation.screens.WeatherConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding: FragmentForecastBinding
        get() = _binding ?: throw RuntimeException("FragmentForecastBinding is null")

    private val dailyAdapter by lazy {
        DailyWeatherAdapter()
    }
    private val viewModel: WeatherConditionsViewModel by activityViewModels()

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
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                with(binding) {
                    viewModel.state.collect { weatherState ->
                        pbForecast.visibility =
                            if (weatherState.isLoading) View.VISIBLE
                            else View.GONE

                        weatherState.error?.let { errorMsg ->
                            Toast.makeText(
                                context,
                                errorMsg,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        weatherState.weatherInfo?.weatherDailyData?.let {
                            dailyAdapter.submitList(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvDailyForecast.adapter = dailyAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}