package com.tessmerandre.advancedtrackerapp.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.tessmerandre.advancedtrackerapp.data.splash.SplashResponse
import com.tessmerandre.advancedtrackerapp.ui.admin.AdminActivity
import com.tessmerandre.advancedtrackerapp.ui.login.LoginActivity
import com.tessmerandre.advancedtrackerapp.ui.operator.OperatorActivity
import com.tessmerandre.advancedtrackerapp.ui.theme.AdvancedTrackerAppTheme
import com.tessmerandre.advancedtrackerapp.worker.UploadPendingLocationsWorker
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

/**
 * Should be using the correct behaviour for splash screens.
 * https://developer.android.com/guide/topics/ui/splash-screen
 * */
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModel()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onPermissionResult
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SplashScreen(viewModel, onSplashResponse = ::onSplashResponse)
                }
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun onSplashResponse(splashResponse: SplashResponse) {
        val intent = when (splashResponse) {
            is SplashResponse.LoggedIn -> {
                if (splashResponse.user.isAdmin()) {
                    Intent(this, AdminActivity::class.java)
                } else {
                    Intent(this, OperatorActivity::class.java)
                }
            }
            else -> Intent(this, LoginActivity::class.java)
        }

        startActivity(intent)
        finish()
    }

    private fun onPermissionResult(isGranted: Boolean) {
        viewModel.onPermissionResult(isGranted)
    }

}

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onSplashResponse: (splashResponse: SplashResponse) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }

    if (viewModel.uiState is SplashUiState.Success) {
        val response = (viewModel.uiState as SplashUiState.Success).response
        onSplashResponse(response)
    }
}