package com.tessmerandre.advancedtrackerapp.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tessmerandre.advancedtrackerapp.data.location.LocationEntity
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.MonthDay
import java.time.ZoneId
import java.util.*

class AdminViewModel(private val repository: LocationRepository) : ViewModel() {

    private val _uiState = MutableSharedFlow<AdminUiState>()
    val uiState: SharedFlow<AdminUiState>
        get() = _uiState

    init {
        getAdminData()
    }

    private fun getAdminData() = viewModelScope.launch {
        _uiState.emit(AdminUiState.Loading)

        val locations = repository.getLocations()
        if (locations.isEmpty()) {
            _uiState.emit(AdminUiState.Empty)
            return@launch
        }

        val data = AdminUiData(
            collectedDays = findCollectedDaysCount(locations),
            travelledDistance = 230L,
            travelledTime = 1_500_000L,
            idleTime = 500_000L
        )

        _uiState.emit(AdminUiState.Data(data))
    }

    private fun findCollectedDaysCount(locations: List<LocationEntity>): Int {
        return locations.map {
            Instant.ofEpochMilli(it.createdAt).atZone(ZoneId.systemDefault()).toLocalDate()
        }.groupBy { it.dayOfMonth }.count()
    }

}

sealed class AdminUiState {
    object Loading : AdminUiState()
    object Empty : AdminUiState()
    class Data(val data: AdminUiData) : AdminUiState()
}

typealias Meters = Long
typealias Milliseconds = Long

fun Milliseconds.toMinutes(): Long {
    return this / 60000
}

data class AdminUiData(
    val collectedDays: Int,
    val travelledDistance: Meters,
    val travelledTime: Milliseconds,
    val idleTime: Milliseconds,
)