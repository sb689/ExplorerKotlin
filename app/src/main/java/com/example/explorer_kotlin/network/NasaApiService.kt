package com.example.explorer_kotlin.network

import com.example.explorer_kotlin.model.SpaceResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://images-api.nasa.gov/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface NasaApiService{

    @GET("search")
    suspend fun getSearchResults(@Query("q") type: String): SpaceResponse
}

object NasaApi{

    val RetrofitService: NasaApiService by lazy{
        retrofit.create(NasaApiService::class.java)
    }
}