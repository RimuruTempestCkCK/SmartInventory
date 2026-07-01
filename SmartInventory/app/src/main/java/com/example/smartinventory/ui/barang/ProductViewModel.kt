package com.example.smartinventory.ui.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.Category
import com.example.smartinventory.data.model.Product
import com.example.smartinventory.data.model.Supplier
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _suppliers = MutableStateFlow<List<Supplier>>(emptyList())
    val suppliers: StateFlow<List<Supplier>> = _suppliers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        refreshData()
    }

    fun refreshData() {
        fetchProducts()
        fetchCategories()
        fetchSuppliers()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getProducts()
                if (response.isSuccessful) _products.value = response.body() ?: emptyList()
            } catch (e: Exception) {
                _message.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getCategories()
                if (response.isSuccessful) _categories.value = response.body() ?: emptyList()
            } catch (e: Exception) {}
        }
    }

    private fun fetchSuppliers() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getSuppliers()
                if (response.isSuccessful) _suppliers.value = response.body() ?: emptyList()
            } catch (e: Exception) {}
        }
    }

    fun addProduct(code: String, name: String, catId: String, supId: String, price: Double, stock: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.insertProduct(code, name, catId, supId, price, stock)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Product added"
                    fetchProducts()
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

    fun editProduct(id: String, code: String, name: String, catId: String, supId: String, price: Double, stock: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.updateProduct(id, code, name, catId, supId, price, stock)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Product updated"
                    fetchProducts()
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

    fun removeProduct(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.deleteProduct(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Product deleted"
                    fetchProducts()
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
