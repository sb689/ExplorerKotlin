package com.example.explorer_kotlin.network

import com.example.explorer_kotlin.model.SpaceResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://images-api.nasa.gov/"
private const val MEDIA_TYPE = "image"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface NasaApiService{

    @GET("search")
    suspend fun getSearchResults(@Query("q") type: String?,
                                 @Query("year_start") startYear: String?,
                                 @Query("year_end") endYear: String?,
                                 @Query("media_type") mediaType: String = MEDIA_TYPE): SpaceResponse

}

object NasaApi{

    val RetrofitService: NasaApiService by lazy{
        retrofit.create(NasaApiService::class.java)
    }
}