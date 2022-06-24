package com.tessmerandre.advancedtrackerapp.data.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Query("SELECT * FROM location")
    fun getLocations(): Flow<List<LocationEntity>>

    @Query("UPDATE location SET sync_status = :status WHERE created_at = :id")
    suspend fun setSyncStatus(id: Long, status: LocationSyncStatus)

    @Query("SELECT * FROM location WHERE sync_status in ('PENDING', 'FAILED')")
    suspend fun findPendingOrFailedLocations(): List<LocationEntity>

}