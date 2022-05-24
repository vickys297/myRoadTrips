package com.example.myroadtrip.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myroadtrip.ui.MapViewModel
import java.lang.Exception

class AppViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = AppRepository.getInstance()
        return when (modelClass) {
            MapViewModel::class.java -> {
                MapViewModel(repository) as T
            }
            else -> {
                throw Exception("No Model Provided")
            }
        }
    }
}