package com.shv.canifly.di.modules

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shv.canifly.data.network.api.ApiFactory
import com.shv.canifly.data.network.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideWeatherApiService(): WeatherApiService {
            return ApiFactory.weatherApiService
        }

        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(
            application: Application
        ): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(application)
        }
    }
}