package com.tessmerandre.advancedtrackerapp.worker

import com.tessmerandre.advancedtrackerapp.data.location.LocationEntity
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import com.tessmerandre.advancedtrackerapp.data.location.LocationSyncStatus
import com.tessmerandre.advancedtrackerapp.data.remote.LocationRemoteDataSource

/**
 * this file should not exist and the logic should be coupled inside the `LocationRepository` or
 * something like this, but for time sake I'll be doing like this.
 * */
class UploadPendingLocationRepository(
    private val locationRepository: LocationRepository,
    private val locationRemoteDataSource: LocationRemoteDataSource
) {

    suspend fun findPendingOrFailedLocations(): List<LocationEntity> =
        locationRepository.findPendingOrFailedLocations()

    suspend fun setLocationSyncStatus(id: Long, status: LocationSyncStatus) =
        locationRepository.setStatus(id, status)

    suspend fun saveToRemote(locationEntity: LocationEntity) =
        locationRemoteDataSource.save(locationEntity)

}