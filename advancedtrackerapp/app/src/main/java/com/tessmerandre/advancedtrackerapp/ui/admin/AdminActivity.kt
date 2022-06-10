package com.tessmerandre.advancedtrackerapp.ui.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tessmerandre.advancedtrackerapp.ui.theme.AdvancedTrackerAppTheme
import org.koin.androidx.compose.getViewModel

class AdminActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AdminScreen {

                    }
                }
            }
        }
    }

}

@Composable
fun AdminScreen(viewModel: AdminViewModel = getViewModel(), onCardClick: () -> Unit) {
    val locations = viewModel.locations.collectAsState(initial = listOf())
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(locations.value) {
                Text(text = "${it.latitude} - ${it.longitude}")
            }
        }
    }
}