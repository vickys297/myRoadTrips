package com.example.myroadtrip.utils

import com.google.android.gms.maps.model.LatLng

object AppConstants {

    object Map {
        const val MAP_KEY = "AIzaSyBxUEdisLpTRMG6-LExrRGUK0W2TlJOfpQ"
        val locationsPreLoad: List<LatLng> = listOf(
            LatLng(9.938825, 76.269354),
            LatLng(11.016730560133844, 76.96922093940562),
            LatLng(9.924476726157804, 78.12281690914091),
            LatLng(10.085682334871859, 77.0601437454977),
            LatLng(9.938825, 76.269354),
        )
    }

    object Network {
        const val BASE_URL = "https://maps.googleapis.com/maps/api/directions/"
    }
}