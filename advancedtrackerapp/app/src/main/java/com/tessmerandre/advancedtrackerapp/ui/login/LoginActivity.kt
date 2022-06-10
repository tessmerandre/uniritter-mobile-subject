package com.tessmerandre.advancedtrackerapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
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

@Composable
fun LoginScreen(viewModel: LoginViewModel = getViewModel(), onLoginSuccess: (isAdmin: Boolean) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginTextField("Email", viewModel.email) {
            viewModel.email = it
        }

        LoginTextField("Password", viewModel.password, PasswordVisualTransformation()) {
            viewModel.password = it
        }

        if (viewModel.uiState is LoginUiState.Error) {
            Text(text = "An error occurred while logging in. Try again later!")
        }

        Button(onClick = viewModel::login) {
            if (viewModel.uiState is LoginUiState.Loading) {
                CircularProgressIndicator(color = Color.Black)
            } else {
                Text("Login")
            }
        }

        if (viewModel.uiState is LoginUiState.Success) {
            Text(text = "Logado com sucesso!")

            val isAdmin = (viewModel.uiState as? LoginUiState.Success)?.isAdmin ?: false
            onLoginSuccess(isAdmin)
        }
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
        onValueChange = { onChange(it) }
    )
}