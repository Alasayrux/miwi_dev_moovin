package com.example.moowin.Home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.File

@Composable
fun OsmMapView(
    userLocation: GeoPoint?,
    transportStops: List<TransportStop>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    remember(context) {
        val prefs = context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        Configuration.getInstance().load(context, prefs)
        Configuration.getInstance().userAgentValue = "MoowinApp/1.0 (Android; package: ${context.packageName})"
        Configuration.getInstance().osmdroidBasePath = File(context.cacheDir, "osmdroid")
        Configuration.getInstance().osmdroidTileCache = File(context.cacheDir, "osmdroid/tiles")
        true
    }

    val defaultGeoPoint = userLocation ?: GeoPoint(19.4326, -99.1332)

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.OpenTopo)
                setMultiTouchControls(true)
                isTilesScaledToDpi = true
                setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)
                controller.setZoom(15.0)
                controller.setCenter(defaultGeoPoint)
                maxZoomLevel = 17.0
                minZoomLevel = 12.0
            }
        },
        update = { mapView ->
            mapView.overlays.clear()

            userLocation?.let { loc ->
                val userMarker = Marker(mapView).apply {
                    position = loc
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Mi Ubicación Actual"
                }
                mapView.overlays.add(userMarker)
                mapView.controller.setCenter(loc)
            }

            transportStops.forEach { stop ->
                val stopMarker = Marker(mapView).apply {
                    position = stop.position
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "${stop.type}: ${stop.name}"
                }
                mapView.overlays.add(stopMarker)
            }

            mapView.invalidate()
        }
    )
}
