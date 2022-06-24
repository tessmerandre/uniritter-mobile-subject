package com.tessmerandre.advancedtrackerapp.ui.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tessmerandre.advancedtrackerapp.ui.theme.AdvancedTrackerAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminActivity : ComponentActivity() {

    private val viewModel: AdminViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state = viewModel.uiState.collectAsState(initial = AdminUiState.Loading)
                    AdminScreen(state = state.value)
                }
            }
        }
    }

}

@Composable
fun AdminScreen(state: AdminUiState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is AdminUiState.Loading -> {
                CircularProgressIndicator()
            }
            is AdminUiState.Empty -> {
                Text("There are no stored locations. Try again later", style = MaterialTheme.typography.h3)
            }
            is AdminUiState.Data -> {
                with(state.data) {
                    Text("Collected days: $collectedDays")
                    Text("Travelled distance: $travelledDistance meters")
                    Text("Travelled time: ${travelledTime.toMinutes()}")
                    Text("Idle time: ${idleTime.toMinutes()}")
                }
            }
        }
    }
}