package com.tessmerandre.advancedtrackerapp.ui.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tessmerandre.advancedtrackerapp.data.splash.SplashRepository
import com.tessmerandre.advancedtrackerapp.data.splash.SplashResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: SplashRepository) : ViewModel() {

    var uiState by mutableStateOf<SplashUiState>(SplashUiState.Loading)
        private set

    private fun load() = viewModelScope.launch {
        delay(500L)
        val response = repository.isUserLogged()
        delay(300L)
        uiState = SplashUiState.Success(response)
    }

    fun onPermissionResult(granted: Boolean) {
        if (granted) {
            load()
        } else {
            uiState = SplashUiState.PermissionError
        }
    }

}

sealed class SplashUiState {
    object Loading : SplashUiState()
    object PermissionError : SplashUiState()
    class Success(val response: SplashResponse) : SplashUiState()
}