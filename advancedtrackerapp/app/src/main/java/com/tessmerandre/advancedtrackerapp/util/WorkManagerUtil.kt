package com.tessmerandre.advancedtrackerapp.util

import android.content.Context
import androidx.work.*
import com.tessmerandre.advancedtrackerapp.worker.UploadPendingLocationsWorker
import java.util.concurrent.TimeUnit

object WorkManagerUtil {

    private const val REPEAT_INTERVAL = 30L
    private const val FLEX_TIME_INTERVAL = 15L
    private const val WORK_NAME = "location_upload_sync"

    fun startSync(context: Context) {
        val work = makeWorkRequestBuilder().setConstraints(makeConstraints()).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

    private fun makeConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    private fun makeWorkRequestBuilder(): PeriodicWorkRequest.Builder {
        return PeriodicWorkRequestBuilder<UploadPendingLocationsWorker>(
            REPEAT_INTERVAL, TimeUnit.MINUTES,
            FLEX_TIME_INTERVAL, TimeUnit.MINUTES
        )
    }

}