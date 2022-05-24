package com.example.myroadtrip.model

import com.google.android.gms.maps.model.LatLng

data class PreLoadLocations(
    var locationData: List<LocationData>? = null,
) {

    data class LocationData(
        var location: LatLng,
        var title: String,
        var colorCode: String
    )

    init {
        locationData = listOf(
            LocationData(LatLng(9.938825, 76.269354), "Start from here, Kochi", "#14C38E"),
            LocationData(LatLng(11.016730560133844, 76.96922093940562), "Coimbatore", "#2F8F9D"),
            LocationData(LatLng(9.924476726157804, 78.12281690914091), "Madurai", "#FF4949"),
            LocationData(LatLng(10.085682334871859, 77.0601437454977), "Munnar", "#AB46D2"),
            LocationData(LatLng(9.938825, 76.269354), "Trip ends here, Kochi", "#0AA1DD"),
        )

    }

}