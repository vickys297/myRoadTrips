package com.example.myroadtrip.ui

import androidx.lifecycle.ViewModel
import com.example.myroadtrip.model.DirectionApiResponse
import com.example.myroadtrip.utils.AppRepository

class MapViewModel(private val repository: AppRepository) : ViewModel() {
    suspend fun getRoutePath(origin: String, destination: String): DirectionApiResponse {
        return repository.getRouteDirection(origin, destination)
    }

}