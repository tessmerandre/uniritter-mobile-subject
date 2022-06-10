package com.tessmerandre.advancedtrackerapp.data.splash

import com.tessmerandre.advancedtrackerapp.data.user.UserEntity

sealed class SplashResponse {
    object LoggedOut : SplashResponse()
    class LoggedIn(val user: UserEntity) : SplashResponse()
}