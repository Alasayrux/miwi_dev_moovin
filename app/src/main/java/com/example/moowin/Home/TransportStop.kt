package com.example.moowin.Home

import org.osmdroid.util.GeoPoint

data class TransportStop(
    val id: String,
    val name: String,
    val position: GeoPoint,
    val type: String
)
