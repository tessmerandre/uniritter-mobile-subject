package com.tessmerandre.advancedtrackerapp.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tessmerandre.advancedtrackerapp.data.location.LocationEntity
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AdminViewModel(private val repository: LocationRepository) : ViewModel() {

    val locations: Flow<List<LocationEntity>>
        get() = repository.getLocations()

}