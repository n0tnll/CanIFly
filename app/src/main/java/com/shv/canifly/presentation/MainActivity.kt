package com.shv.canifly.presentation

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.shv.canifly.R
import com.shv.canifly.databinding.ActivityMainBinding
import com.shv.canifly.presentation.SplashFragment.Companion.SPLASH_KEY
import com.shv.canifly.presentation.onboarding.LocationRequestFragment.Companion.LOCATION_KEY
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.FINISHED_KEY
import com.shv.canifly.presentation.onboarding.OnBoardingFragment.Companion.ONBOARDING_KEY
import com.shv.canifly.presentation.screens.WeatherConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: WeatherConditionsViewModel by viewModels()
    private var isSplashScreenClosed = false
    private var isOnboardingFinished = false
    private var isLocationEnabled = false
    private var isBottomBarShow = false

    private val sharedPreferences by lazy { getSharedPreferences(ONBOARDING_KEY, MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigation()


        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        getSettings(sharedPreferences)
    }

    private fun getSettings(sharedPreferences: SharedPreferences) {
        isSplashScreenClosed = sharedPreferences.getBoolean(SPLASH_KEY, false)
        isOnboardingFinished = sharedPreferences.getBoolean(FINISHED_KEY, false)
        isLocationEnabled = sharedPreferences.getBoolean(LOCATION_KEY, false)
        isBottomBarShow = isOnboardingFinished && isSplashScreenClosed

        if (isLocationEnabled) viewModel.loadWeatherInfo()
        if (isBottomBarShow) binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment
        val navController = navHostFragment.navController

        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.watchingDateFragment)
        badge.isVisible = true
        badge.number = 2

        setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.let {
            if (it == LOCATION_KEY) {
                isLocationEnabled = sharedPreferences?.getBoolean(it, false) ?: false
            }
            if (it == FINISHED_KEY) {
                isOnboardingFinished = sharedPreferences?.getBoolean(it, false) ?: false
            }
            if (it == SPLASH_KEY) {
                isSplashScreenClosed = sharedPreferences?.getBoolean(it, false) ?: false
            }
        }
        sharedPreferences?.let {
            getSettings(it)
        }
    }

    override fun onDestroy() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }
}