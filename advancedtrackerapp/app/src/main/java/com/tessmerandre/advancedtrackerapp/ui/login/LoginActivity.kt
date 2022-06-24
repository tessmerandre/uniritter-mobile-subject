package com.tessmerandre.advancedtrackerapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tessmerandre.advancedtrackerapp.ui.admin.AdminActivity
import com.tessmerandre.advancedtrackerapp.ui.operator.OperatorActivity
import com.tessmerandre.advancedtrackerapp.ui.theme.AdvancedTrackerAppTheme
import org.koin.androidx.compose.getViewModel

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(onLoginSuccess = ::onLoginSucceed)
                }
            }
        }
    }

    private fun onLoginSucceed(isAdmin: Boolean) {
        val intent = if (isAdmin) {
            Intent(this, AdminActivity::class.java)
        } else {
            Intent(this, OperatorActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(onLoginSuccess = {})
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = getViewModel(),
    onLoginSuccess: (isAdmin: Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(24.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Advanced Tracker", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(24.dp))

            LoginTextField("Email", viewModel.email) {
                viewModel.email = it
            }

            LoginTextField("Password", viewModel.password, PasswordVisualTransformation()) {
                viewModel.password = it
            }

            if (viewModel.uiState is LoginUiState.Error) {
                Text(
                    text = "An error occurred while logging in. Try again later!",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = viewModel::login,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (viewModel.uiState is LoginUiState.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                    )
                } else {
                    Text("Login")
                }
            }

            if (viewModel.uiState is LoginUiState.Success) {
                Text(text = "Login successful!")

                val isAdmin = (viewModel.uiState as? LoginUiState.Success)?.isAdmin ?: false
                onLoginSuccess(isAdmin)
            }
        }

        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
fun LoginTextField(
    label: String,
    value: String,
    transformation: VisualTransformation = VisualTransformation.None,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        visualTransformation = transformation,
        label = { Text(label) },
        onValueChange = { onChange(it) },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.body1,
        maxLines = 1
    )
}