package com.tessmerandre.advancedtrackerapp.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user_type") val userType: UserType,
    @ColumnInfo(name = "email") val email: String,
) {

    fun isAdmin() = userType == UserType.ADMIN

    companion object {
        fun admin() = UserEntity(1, name = "ADMIN USER", userType = UserType.ADMIN, email = "admin")
        fun operator() = UserEntity(1, name = "OPERATOR USER", userType = UserType.OPERATOR, email = "operator")
    }
}

enum class UserType {
    ADMIN, OPERATOR
}