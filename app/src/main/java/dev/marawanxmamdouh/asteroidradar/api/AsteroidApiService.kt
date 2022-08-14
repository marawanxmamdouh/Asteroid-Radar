package dev.marawanxmamdouh.asteroidradar.api

import dev.marawanxmamdouh.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getProperties(@Query("api_key") type: String): String
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}