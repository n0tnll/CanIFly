package com.shv.canifly.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.shv.canifly.data.network.api.ApiFactory
import com.shv.canifly.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val apiService = ApiFactory.weatherApiService

        binding.btnLoadCurrentWeather.setOnClickListener {
            lifecycleScope.launch {
                apiService.getWeatherConditionData(lat = 43.1056, long = 131.8735)
            }
        }
    }
}