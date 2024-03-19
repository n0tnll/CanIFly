package com.shv.canifly.data.repository

import com.shv.canifly.data.mapper.toAirportsList
import com.shv.canifly.data.mapper.toWeatherInfo
import com.shv.canifly.data.network.api.ApiFactory
import com.shv.canifly.data.network.api.ApiService
import com.shv.canifly.domain.entity.Airport
import com.shv.canifly.domain.entity.WeatherInfo
import com.shv.canifly.domain.repository.Repository
import com.shv.canifly.domain.util.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = apiService.getWeatherConditionData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getNfzData(): Resource<List<Airport>> {
        return try {
            val csv = ApiFactory.createCsvApiService()
            val response = csv.getNFZDataCSV()
            if (response.isSuccessful) {
                Resource.Success(
                    data = response.body()?.string().toAirportsList()
                )
            } else {
                Resource.Error(
                    message = "Download data error: ${response.errorBody()}"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "getNfzData(): An unknown error occurred.")
        }
    }
}