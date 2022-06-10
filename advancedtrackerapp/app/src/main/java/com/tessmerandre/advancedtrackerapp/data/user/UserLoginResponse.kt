package com.tessmerandre.advancedtrackerapp.data.user

sealed class UserLoginResponse {
    object Error : UserLoginResponse()
    class Success(val user: UserEntity) : UserLoginResponse()
}