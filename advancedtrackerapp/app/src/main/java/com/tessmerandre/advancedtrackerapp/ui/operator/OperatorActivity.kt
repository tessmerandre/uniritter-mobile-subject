package com.tessmerandre.advancedtrackerapp.ui.operator

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.tessmerandre.advancedtrackerapp.service.LocationUpdatesService
import com.tessmerandre.advancedtrackerapp.ui.theme.AdvancedTrackerAppTheme
import com.tessmerandre.advancedtrackerapp.util.WorkManagerUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class OperatorActivity : ComponentActivity() {

    companion object {
        private const val DEFAULT_MS = 3000L
    }

    private val viewModel: OperatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedTrackerAppTheme {
                val points = viewModel.mapPoints.collectAsState(initial = listOf())
                var sliderValue by remember { mutableStateOf(0f) }
                val cameraPosition = points.value.lastOrNull() ?: LatLng(-29.9877473, -51.1243852)

                Box(Modifier.fillMaxSize()) {
                    MapView(cameraPosition = cameraPosition, points = points.value)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colors.surface)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Change the delay between each location update",
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = "3 -- 180 seconds",
                            style = MaterialTheme.typography.caption
                        )
                        Slider(value = sliderValue, steps = 180, onValueChange = { newValue ->
                            sliderValue = newValue
                        }, onValueChangeFinished = {
                            startService(viewModel.calculateNewIntervalInMilliseconds(sliderValue))
                        })
                    }
                }
            }
        }

        startService(DEFAULT_MS)
        WorkManagerUtil.startSync(this)
    }

    @Composable
    private fun MapView(cameraPosition: LatLng, points: List<LatLng>) {
        GoogleMap(
            properties = makeMapProperties(),
            cameraPositionState = CameraPositionState(
                CameraPosition.fromLatLngZoom(
                    cameraPosition,
                    18f
                )
            )
        ) {
            Polyline(points = points)
        }
    }

    private fun startService(interval: Long) {
        stopService(Intent(this, LocationUpdatesService::class.java))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(LocationUpdatesService.newIntent(this, interval))
        } else {
            startService(LocationUpdatesService.newIntent(this, interval))
        }
    }

    private fun makeMapProperties() = MapProperties(
        maxZoomPreference = 25f,
        minZoomPreference = 10f,
        isMyLocationEnabled = true
    )
}