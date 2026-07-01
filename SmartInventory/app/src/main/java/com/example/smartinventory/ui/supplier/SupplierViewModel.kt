package com.example.smartinventory.ui.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.Supplier
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupplierViewModel : ViewModel() {
    private val _suppliers = MutableStateFlow<List<Supplier>>(emptyList())
    val suppliers: StateFlow<List<Supplier>> = _suppliers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        fetchSuppliers()
    }

    fun fetchSuppliers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getSuppliers()
                if (response.isSuccessful) {
                    _suppliers.value = response.body() ?: emptyList()
                } else {
                    _message.value = "Failed to fetch suppliers"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addSupplier(name: String, address: String, phone: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.insertSupplier(name, address, phone)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Supplier added successfully"
                    fetchSuppliers()
                } else {
                    _message.value = response.body()?.message ?: "Failed to add supplier"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun editSupplier(id: String, name: String, address: String, phone: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.updateSupplier(id, name, address, phone)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Supplier updated successfully"
                    fetchSuppliers()
                } else {
                    _message.value = response.body()?.message ?: "Failed to update supplier"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeSupplier(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.deleteSupplier(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Supplier deleted successfully"
                    fetchSuppliers()
                } else {
                    _message.value = response.body()?.message ?: "Failed to delete supplier"
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
