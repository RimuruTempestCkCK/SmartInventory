package com.example.smartinventory.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.User
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getUsers()
                if (response.isSuccessful) _users.value = response.body() ?: emptyList()
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addUser(username: String, pass: String, role: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.insertUser(username, pass, role)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "User ditambahkan"
                    fetchUsers()
                } else {
                    _message.value = response.body()?.message ?: "Gagal"
                }
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun editUser(id: String, username: String, pass: String, role: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.updateUser(id, username, pass, role)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "User diperbarui"
                    fetchUsers()
                } else {
                    _message.value = response.body()?.message ?: "Gagal"
                }
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeUser(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.deleteUser(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "User dihapus"
                    fetchUsers()
                }
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() { _message.value = null }
}
