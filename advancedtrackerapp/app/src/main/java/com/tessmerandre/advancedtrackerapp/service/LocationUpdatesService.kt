package com.tessmerandre.advancedtrackerapp.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.tessmerandre.advancedtrackerapp.R
import com.tessmerandre.advancedtrackerapp.data.location.LocationRepository
import com.tessmerandre.advancedtrackerapp.di.Named
import com.tessmerandre.advancedtrackerapp.ui.splash.SplashActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class LocationUpdatesService : Service(), KoinComponent {

    inner class LocalBinder : Binder()

    private val locationRepository: LocationRepository by inject()
    private val coroutineScope: CoroutineScope by inject(named(Named.COROUTINE_SCOPE))

    private val binder: IBinder = LocalBinder()

    private val notificationManager: NotificationManager by lazy {
        makeNotificationManager()
    }

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private lateinit var locationRequest: LocationRequest
    private val locationCallback = makeLocationCallback()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val interval = intent?.getLongExtra(ARG_LOCATION_UPDATE_INTERVAL_MS, 6000) ?: 6000L
        val fastestInterval = interval / 2

        locationRequest = makeLocationRequest(interval, fastestInterval)
        requestLocationUpdates()

        notificationManager.notify(
            NOTIFICATION_ID,
            makeNotification()
        )

        startForeground(NOTIFICATION_ID, makeNotification())

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
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

    private fun makeNotification(): Notification {
        val servicePendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, SplashActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .addAction(
                R.drawable.ic_launcher_background, "Open app",
                servicePendingIntent
            )
            .setContentTitle("Tracking your location")
            .setContentText("We are tracking your location in the background")
            .setOngoing(true)
            .setPriority(Notification.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setWhen(System.currentTimeMillis())
            .build()
    }

    private fun onNewLocation(location: Location?) {
        if (location == null) return
        saveNewLocation(location)
    }

    private fun makeLocationRequest(interval: Long, fastestInterval: Long): LocationRequest {
        return LocationRequest.create().apply {
            this.interval = interval
            this.fastestInterval = fastestInterval
            this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
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

    private fun saveNewLocation(location: Location) = coroutineScope.launch {
        locationRepository.save(location.latitude, location.longitude)
    }

    companion object {
        private const val CHANNEL_ID = "channel_01"
        private const val NOTIFICATION_ID = 12345678

        const val ARG_LOCATION_UPDATE_INTERVAL_MS = "location_update_interval_ms"

        fun newIntent(context: Context, interval: Long): Intent {
            val intent = Intent(context, LocationUpdatesService::class.java)
            intent.putExtra(ARG_LOCATION_UPDATE_INTERVAL_MS, interval)
            return intent
        }
    }
}