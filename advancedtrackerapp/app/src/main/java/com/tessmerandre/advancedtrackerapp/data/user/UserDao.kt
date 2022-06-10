package com.tessmerandre.advancedtrackerapp.data.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: UserEntity): Long

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun findFirst(): UserEntity?

    @Query("DELETE FROM user")
    suspend fun delete()

}