package com.shv.canifly.di.modules

import com.shv.canifly.data.location.DefaultLocationTracker
import com.shv.canifly.data.repository.WeatherRepositoryImpl
import com.shv.canifly.domain.location.LocationTracker
import com.shv.canifly.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    fun bindLocationTracker(impl: DefaultLocationTracker): LocationTracker
}