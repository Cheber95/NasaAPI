package com.example.nasaapi.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/planetary/apod/"

interface PictureOfTheDayAPI {

    @GET(BASE_URL)
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PODServerResponseData>
}