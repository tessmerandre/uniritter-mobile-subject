package com.tessmerandre.advancedtrackerapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tessmerandre.advancedtrackerapp.data.location.LocationSyncStatus
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UploadPendingLocationsWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val repository: UploadPendingLocationRepository by inject()

    override suspend fun doWork(): Result {
        val locations = repository.findPendingOrFailedLocations()

        for (location in locations) {
            repository.setLocationSyncStatus(location.createdAt, LocationSyncStatus.IN_PROGRESS)
            val result = repository.saveToRemote(location)

            if (result.isSuccess) {
                repository.setLocationSyncStatus(location.createdAt, LocationSyncStatus.SYNCED)
            } else {
                repository.setLocationSyncStatus(location.createdAt, LocationSyncStatus.FAILED)
            }
        }

        return Result.success()
    }

}