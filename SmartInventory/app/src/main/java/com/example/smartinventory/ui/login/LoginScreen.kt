package com.example.smartinventory.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.preference.PreferenceManager
import com.example.smartinventory.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current
    val prefManager = remember { PreferenceManager(context) }

    LaunchedEffect(loginState) {
        if (loginState is LoginResult.Success) {
            val user = (loginState as LoginResult.Success).response.user
            if (user != null) {
                prefManager.saveLoginStatus(true)
                prefManager.saveUser(
                    user.id ?: "",
                    user.username,
                    user.name ?: "",
                    user.level ?: ""
                )
                onLoginSuccess()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DiscordCanvas),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = DiscordSurfaceIndigo
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "We're so excited to see you again!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.LightGray,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("USERNAME", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = DiscordSurfaceBlack,
                        unfocusedContainerColor = DiscordSurfaceBlack,
                        focusedBorderColor = DiscordBlurple,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(4.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("PASSWORD", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = DiscordSurfaceBlack,
                        unfocusedContainerColor = DiscordSurfaceBlack,
                        focusedBorderColor = DiscordBlurple,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(4.dp),
                    singleLine = true
                )

                if (loginState is LoginResult.Error) {
                    Text(
                        text = (loginState as LoginResult.Error).message,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.login(username, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DiscordBlurple
                    ),
                    shape = RoundedCornerShape(4.dp),
                    enabled = loginState !is LoginResult.Loading
                ) {
                    if (loginState is LoginResult.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Log In", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
