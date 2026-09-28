package com.example.moowin.Home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moowin.location.LocationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.osmdroid.util.GeoPoint

data class HomeUiState(
    val searchQuery: String = "",
    val userLocation: GeoPoint? = null,
    val isLoadingLocation: Boolean = false,
    val transportStops: List<TransportStop> = listOf() // CONSULTAR API . RELLENAR POR TILE O PARA RECORRIDOS
)

class HomeViewModel : ViewModel() {
    private val searchQueryFlow = MutableStateFlow("")

    val uiState: StateFlow<HomeUiState> = combine(
        searchQueryFlow,
        LocationManager.currentLocation,
        LocationManager.isLoading
    ) { query, location, loading ->
        HomeUiState(
            searchQuery = query,
            userLocation = location,
            isLoadingLocation = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    fun onSearchQueryChanged(query: String) {
        searchQueryFlow.update { query }
    }

    fun fetchCurrentLocation(context: Context) {
        LocationManager.fetchCurrentLocation(context)
    }
}
