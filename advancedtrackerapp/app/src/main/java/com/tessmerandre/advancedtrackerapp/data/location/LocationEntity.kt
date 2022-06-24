package com.tessmerandre.advancedtrackerapp.data.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "location")
data class LocationEntity(
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "sync_status") val syncStatus: LocationSyncStatus = LocationSyncStatus.PENDING,
    @ColumnInfo(name = "created_at") @PrimaryKey val createdAt: Long = Date().time
)