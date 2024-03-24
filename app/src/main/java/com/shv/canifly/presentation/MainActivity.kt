package com.shv.canifly.presentation

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.shv.canifly.R
import com.shv.canifly.databinding.ActivityMainBinding
import com.shv.canifly.presentation.viewmodels.WeatherConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val viewModel: WeatherConditionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            binding.bottomNavigation.visibility = View.GONE
            delay(3000)
            binding.bottomNavigation.visibility = View.VISIBLE
        }
        requestLocationPermission()
        setupBottomNavigation()
    }

    private fun requestLocationPermission() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
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
}