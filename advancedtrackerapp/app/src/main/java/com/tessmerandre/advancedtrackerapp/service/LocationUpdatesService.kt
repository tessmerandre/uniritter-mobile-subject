package com.tessmerandre.advancedtrackerapp.service

import android.app.*
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.tessmerandre.advancedtrackerapp.R
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import com.tessmerandre.advancedtrackerapp.ui.splash.SplashActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocationUpdatesService : Service(), KoinComponent {

    inner class LocalBinder : Binder() {
        val service: LocationUpdatesService
            get() = this@LocationUpdatesService
    }

    private val locationRepository: LocationRepository by inject()
    private val binder: IBinder = LocalBinder()

    private val notificationManager: NotificationManager by lazy {
        makeNotificationManager()
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val locationRequest = makeLocationRequest()
    private val locationCallback = makeLocationCallback()

    override fun onCreate() {
        requestLocationUpdates()
        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    override fun onBind(intent: Intent): IBinder {
        stopForeground(true)
        return binder
    }

    override fun onRebind(intent: Intent) {
        stopForeground(true)
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        startForeground(NOTIFICATION_ID, notification)
        return true
    }

    private fun requestLocationUpdates() {
        try {
            val looper = Looper.myLooper() ?: return
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                looper
            )
        } catch (unlikely: SecurityException) {
            Log.e(LocationUpdatesService::class.simpleName, "Permission not granted", unlikely)
        }
    }

    private val notification: Notification
        get() {
            val servicePendingIntent = PendingIntent.getActivity(
                this,
                0,
                Intent(this, SplashActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            return NotificationCompat.Builder(this, CHANNEL_ID)
                .addAction(
                    R.drawable.ic_launcher_background, "abrir app",
                    servicePendingIntent
                )
                .setContentTitle("CONTENT TITLE")
                .setContentText("CONTENT TEXT")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build()
        }

    private fun onNewLocation(location: Location) {
        saveNewLocation(location)
    }

    private fun makeLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun makeLocationCallback(): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                onNewLocation(result.lastLocation)
            }
        }
    }

    private fun makeNotificationManager(): NotificationManager {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Advanced tracker application",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        return notificationManager
    }

    private fun saveNewLocation(location: Location) {
        GlobalScope.launch {
            locationRepository.save(location.latitude, location.longitude)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_01"
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
        private const val NOTIFICATION_ID = 12345678
    }
}