package com.tessmerandre.advancedtrackerapp.data.logout

import com.tessmerandre.advancedtrackerapp.data.user.UserDao
import kotlinx.coroutines.delay

class LogoutUseCase(private val userDao: UserDao) {

    suspend fun logout(): Boolean {
        delay(300L)
        userDao.delete()
        return true
    }

}