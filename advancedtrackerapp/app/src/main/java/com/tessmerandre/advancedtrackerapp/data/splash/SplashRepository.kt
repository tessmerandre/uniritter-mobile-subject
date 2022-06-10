package com.tessmerandre.advancedtrackerapp.data.splash

import com.tessmerandre.advancedtrackerapp.data.user.UserDao
import com.tessmerandre.advancedtrackerapp.data.user.UserEntity

class SplashRepository(private val userDao: UserDao) {

    private suspend fun getCurrentUser(): UserEntity? {
        return userDao.findFirst()
    }

    suspend fun isUserLogged(): SplashResponse {
        val currentUser = getCurrentUser() ?: return SplashResponse.LoggedOut
        return SplashResponse.LoggedIn(currentUser)
    }

}