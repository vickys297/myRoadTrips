package com.example.myroadtrip.network

import com.example.myroadtrip.model.DirectionAPIResponseData
import com.example.myroadtrip.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DirectionAPI {

    @GET("json")
    suspend fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String = AppConstants.Map.MAP_KEY,
    ): Response<DirectionAPIResponseData>
}