package com.shv.canifly.data.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://api.open-meteo.com/"
    private const val CSV_URL = "https://davidmegginson.github.io/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApiService: ApiService = retrofit.create(ApiService::class.java)

    fun createCsvApiService(): ApiService {
        val csvOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val csvRetrofit = Retrofit.Builder()
            .baseUrl(CSV_URL)
            .client(csvOkHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        return csvRetrofit.create(ApiService::class.java)
    }
}