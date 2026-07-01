package com.example.smartinventory.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.LoginResponse
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginResult>(LoginResult.Idle)
    val loginState: StateFlow<LoginResult> = _loginState

    fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _loginState.value = LoginResult.Error("Username and password required")
            return
        }

        viewModelScope.launch {
            _loginState.value = LoginResult.Loading
            try {
                val response = RetrofitClient.instance.login(username, password)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status == true) {
                        _loginState.value = LoginResult.Success(body)
                    } else {
                        _loginState.value = LoginResult.Error(body?.message ?: "Login failed")
                    }
                } else {
                    _loginState.value = LoginResult.Error("Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginResult.Error("Network error: ${e.message}")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginResult.Idle
    }
}

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    data class Success(val response: LoginResponse) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
