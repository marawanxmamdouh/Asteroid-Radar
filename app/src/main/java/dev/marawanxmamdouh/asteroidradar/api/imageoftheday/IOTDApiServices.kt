package dev.marawanxmamdouh.asteroidradar.api.imageoftheday

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.marawanxmamdouh.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface IOTDApiServices {
    @GET("planetary/apod/")
    suspend fun getProperties(@Query("api_key") type: String): ImageOfTheDay
}

object IOTDApi {
    val retrofitService: IOTDApiServices by lazy {
        retrofit.create(IOTDApiServices::class.java)
    }
}