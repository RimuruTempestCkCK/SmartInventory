package com.example.smartinventory.ui.stok

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.Product
import com.example.smartinventory.data.model.Transaction
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {
    private val _history = MutableStateFlow<List<Transaction>>(emptyList())
    val history: StateFlow<List<Transaction>> = _history

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        fetchProducts()
        fetchHistory()
    }

    fun fetchHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getHistory()
                if (response.isSuccessful) _history.value = response.body() ?: emptyList()
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getProducts()
                if (response.isSuccessful) _products.value = response.body() ?: emptyList()
            } catch (e: Exception) {}
        }
    }

    fun addStockIn(productId: String, qty: Int, date: String, info: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.stockIn(productId, qty, date, info)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Stock In recorded"
                    fetchHistory()
                }
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addStockOut(productId: String, qty: Int, date: String, info: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.stockOut(productId, qty, date, info)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Stock Out recorded"
                    fetchHistory()
                } else {
                    _message.value = response.body()?.message ?: "Error"
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
