package com.tessmerandre.advancedtrackerapp.ui.operator

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.tessmerandre.advancedtrackerapp.service.LocationUpdatesService
import com.tessmerandre.advancedtrackerapp.ui.theme.AdvancedTrackerAppTheme

class OperatorActivity : ComponentActivity() {

    private var service: LocationUpdatesService? = null
    private var bound = false

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: LocationUpdatesService.LocalBinder =
                service as LocationUpdatesService.LocalBinder
            this@OperatorActivity.service = binder.service
            bound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            this@OperatorActivity.service = null
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedTrackerAppTheme {
//                var mapProperties by remember {
//                    mutableStateOf(
//                        MapProperties(maxZoomPreference = 10f, minZoomPreference = 5f)
//                    )
//                }
//                var mapUiSettings by remember {
//                    mutableStateOf(
//                        MapUiSettings(mapToolbarEnabled = false)
//                    )
//                }
//                Box(Modifier.fillMaxSize()) {
//                    GoogleMap(properties = mapProperties, uiSettings = mapUiSettings)
//                    Column {
//                        Button(onClick = {
//                            mapProperties = mapProperties.copy(
//                                isBuildingEnabled = !mapProperties.isBuildingEnabled
//                            )
//                        }) {
//                            Text(text = "Toggle isBuildingEnabled")
//                        }
//                        Button(onClick = {
//                            mapUiSettings = mapUiSettings.copy(
//                                mapToolbarEnabled = !mapUiSettings.mapToolbarEnabled
//                            )
//                        }) {
//                            Text(text = "Toggle mapToolbarEnabled")
//                        }
//                    }
//                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = "Operator Activity")
                }
            }
        }


        // Bind to the service. If the service is in foreground mode, this signals to the service
        // that since this activity is in the foreground, the service can exit foreground mode.
        bindService(
            Intent(this, LocationUpdatesService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
    }
}