package com.example.myroadtrip.utils

import com.example.myroadtrip.model.DirectionApiResponse
import com.example.myroadtrip.network.RetrofitService
import java.lang.Exception

class AppRepository {


    companion object {
        fun getInstance() = AppRepository()
    }

    suspend fun getRouteDirection(origin: String, destination: String): DirectionApiResponse {
        val request = RetrofitService.getInstance().createService()
        return try {

            val response = request.getDirection(origin = origin, destination = destination)
            val directionData = response.body()!!
            if (response.isSuccessful) {
                when (directionData.status) {
                    "OK" -> {
                        DirectionApiResponse.SUCCESS(directionData)
                    }
                    "ZERO_RESULTS" -> {
                        DirectionApiResponse.ERROR(error = "No path found")
                    }
                    else -> {
                        DirectionApiResponse.ERROR(error = response.message())
                    }
                }
            } else {
                DirectionApiResponse.ERROR(error = response.message())
            }
        } catch (e: Exception) {
            DirectionApiResponse.EXCEPTION(exception = e.localizedMessage!!)
        }
    }
}