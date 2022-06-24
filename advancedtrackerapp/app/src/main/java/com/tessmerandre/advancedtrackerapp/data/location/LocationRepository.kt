package com.tessmerandre.advancedtrackerapp.data.location

class LocationRepository(private val locationDao: LocationDao) {

    fun getLocations() = locationDao.getLocations()

    suspend fun save(latitude: Double, longitude: Double) {
        val location = LocationEntity(latitude, longitude)
        locationDao.insert(location)
    }

    suspend fun findPendingOrFailedLocations() = locationDao.findPendingOrFailedLocations()

    suspend fun setStatus(id: Long, status: LocationSyncStatus) =
        locationDao.setSyncStatus(id, status)

}