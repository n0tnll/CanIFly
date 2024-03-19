package com.shv.canifly.di

import com.shv.canifly.data.location.DefaultLocationTracker
import com.shv.canifly.data.repository.RepositoryImpl
import com.shv.canifly.domain.location.LocationTracker
import com.shv.canifly.domain.repository.Repository
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
    fun bindWeatherRepository(impl: RepositoryImpl): Repository

    @Binds
    @Singleton
    fun bindLocationTracker(impl: DefaultLocationTracker): LocationTracker
}