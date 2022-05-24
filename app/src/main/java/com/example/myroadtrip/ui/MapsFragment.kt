package com.example.myroadtrip.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myroadtrip.R
import com.example.myroadtrip.databinding.FragmentMapsBinding
import com.example.myroadtrip.model.DirectionApiResponse
import com.example.myroadtrip.model.PreLoadLocations
import com.example.myroadtrip.utils.AppViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.JointType.ROUND
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.launch


class MapsFragment : Fragment(R.layout.fragment_maps) {


    private lateinit var binding: FragmentMapsBinding
    private lateinit var viewModel: MapViewModel

    private val callback = OnMapReadyCallback { googleMap ->

        val location: LatLngBounds = LatLngBounds(
            LatLng(10.269746121880367, 77.01817859423217),
            LatLng(10.269746121880367, 77.01817859423217),
        )

        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                requireContext(),
                R.raw.styled_map
            )
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.center, 7F));

        getRoutePath(googleMap, PreLoadLocations())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)
        viewModel = ViewModelProvider(this, AppViewModelFactory())[MapViewModel::class.java]
        val mapFragment =
            childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    private fun getRoutePath(googleMap: GoogleMap, preLoadLocation: PreLoadLocations) {

        viewLifecycleOwner.lifecycleScope.launch {

            for (index in 0 until preLoadLocation.locationData!!.size - 1) {

                val response = viewModel.getRoutePath(
                    String.format(
                        "%f,%f",
                        preLoadLocation.locationData!![index].location.latitude,
                        preLoadLocation.locationData!![index].location.longitude
                    ),
                    String.format(
                        "%f,%f",
                        preLoadLocation.locationData!![index + 1].location.latitude,
                        preLoadLocation.locationData!![index + 1].location.longitude
                    )
                )
                when (response) {
                    is DirectionApiResponse.SUCCESS -> {

                        for (route in response.data.routes) {
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(preLoadLocation.locationData!![index].location)
                                    .title(preLoadLocation.locationData!![index].title)
                            )

                            googleMap.addPolyline(
                                PolylineOptions()
                                    .startCap(SquareCap())
                                    .endCap(SquareCap())
                                    .jointType(ROUND)
                                    .color(Color.parseColor(preLoadLocation.locationData!![index].colorCode))
                                    .addAll(PolyUtil.decode(route.overviewPolyline.points))
                            )
                        }

                    }
                    is DirectionApiResponse.ERROR -> {
                        Snackbar.make(binding.map, response.error, Snackbar.LENGTH_LONG).show()
                    }
                    is DirectionApiResponse.EXCEPTION -> {
                        println(response.exception)
                        Snackbar.make(binding.map, "Something went wrong", Snackbar.LENGTH_LONG)
                            .show()

                    }
                }
            }
        }
    }
}