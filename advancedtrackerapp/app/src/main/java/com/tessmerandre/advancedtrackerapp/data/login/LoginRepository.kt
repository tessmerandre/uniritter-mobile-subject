package com.tessmerandre.advancedtrackerapp.data.login

import com.tessmerandre.advancedtrackerapp.data.user.*
import kotlinx.coroutines.delay

class LoginRepository(private val dao: UserDao) {

    private val adminUser = "admin"
    private val operatorUser = "operator"
    private val availableUsers = listOf(adminUser, operatorUser)

    /**
     * This function should check if the user exists, but since we don't have any way to know it,
     * for now we are just mocking and returning based on the entered email.
     * */
    suspend fun login(loginRequest: UserLoginRequest): UserLoginResponse {
        delay(500L)

        if (loginRequest.email !in availableUsers) return UserLoginResponse.Error
        if (loginRequest.email.isBlank()) return UserLoginResponse.Error

        delay(500L)

        val user = if (loginRequest.email == adminUser) UserEntity.admin()
        else UserEntity.operator()

        dao.save(user)

        return UserLoginResponse.Success(user)
    }

}