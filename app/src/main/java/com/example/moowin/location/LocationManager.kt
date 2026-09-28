package com.example.moowin.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.osmdroid.util.GeoPoint

object LocationManager {
    private val _currentLocation = MutableStateFlow<GeoPoint?>(null)
    val currentLocation: StateFlow<GeoPoint?> = _currentLocation.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(context: Context) {
        _isLoading.update { true }
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geoPoint = GeoPoint(location.latitude, location.longitude)
                _currentLocation.update { geoPoint }
            }
            _isLoading.update { false }
        }.addOnFailureListener {
            _isLoading.update { false }
        }
    }
}
