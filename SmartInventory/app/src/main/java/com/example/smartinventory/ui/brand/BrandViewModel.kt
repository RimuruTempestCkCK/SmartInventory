package com.example.smartinventory.ui.brand

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.Brand
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BrandViewModel : ViewModel() {
    private val _brands = MutableStateFlow<List<Brand>>(emptyList())
    val brands: StateFlow<List<Brand>> = _brands

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        fetchBrands()
    }

    fun fetchBrands() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getBrands()
                if (response.isSuccessful) {
                    _brands.value = response.body() ?: emptyList()
                } else {
                    _message.value = "Gagal memuat brand"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addBrand(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.insertBrand(name)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Brand ditambahkan"
                    fetchBrands()
                } else {
                    _message.value = response.body()?.message ?: "Gagal menambah brand"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun editBrand(id: String, name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.updateBrand(id, name)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Brand diperbarui"
                    fetchBrands()
                } else {
                    _message.value = response.body()?.message ?: "Gagal memperbarui brand"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeBrand(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.deleteBrand(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Brand dihapus"
                    fetchBrands()
                } else {
                    _message.value = response.body()?.message ?: "Gagal menghapus brand"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}
