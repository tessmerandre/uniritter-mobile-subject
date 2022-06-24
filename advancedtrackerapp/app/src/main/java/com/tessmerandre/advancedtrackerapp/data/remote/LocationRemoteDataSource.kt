package com.tessmerandre.advancedtrackerapp.data.remote

import com.tessmerandre.advancedtrackerapp.data.location.LocationEntity
import com.tessmerandre.advancedtrackerapp.data.location.LocationSyncStatus
import kotlinx.coroutines.delay

class LocationRemoteDataSource{

    /**
     * mocking it.
     *
     * should be doing request to save it. the return value will probably be different, but
     * for time sake just returning a result with the updated location entity
     * */
    suspend fun save(locationEntity: LocationEntity): Result<LocationEntity> {
        delay(800L)
        return Result.success(locationEntity.copy(syncStatus = LocationSyncStatus.SYNCED))
    }

}