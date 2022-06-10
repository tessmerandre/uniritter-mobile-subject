package com.tessmerandre.advancedtrackerapp.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tessmerandre.advancedtrackerapp.data.login.LoginRepository
import com.tessmerandre.advancedtrackerapp.data.user.UserLoginRequest
import com.tessmerandre.advancedtrackerapp.data.user.UserLoginResponse
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun login() {
        viewModelScope.launch {
            uiState = LoginUiState.Loading

            val request = makeUserLoginRequest()
            val response = repository.login(request)

            uiState = if (response is UserLoginResponse.Success) {
                LoginUiState.Success(response.user.isAdmin())
            } else {
                LoginUiState.Error
            }
        }
    }

    private fun makeUserLoginRequest() = UserLoginRequest(email, password)

}

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Error : LoginUiState()
    object Loading : LoginUiState()
    class Success(val isAdmin: Boolean) : LoginUiState()
}