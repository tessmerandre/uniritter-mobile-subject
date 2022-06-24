package com.tessmerandre.advancedtrackerapp.ui.operator

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.tessmerandre.advancedtrackerapp.data.location.LocationEntity
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.max

class OperatorViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    private val locations: Flow<List<LocationEntity>>
        get() = locationRepository.getLocations()

    val mapPoints = locations.map { locations ->
        locations.map { LatLng(it.latitude, it.longitude) }
    }

    fun calculateNewIntervalInMilliseconds(sliderValue: Float): Long {
        val value = (sliderValue * 180 * 1000).toLong()
        return max(3000L, value)
    }

}