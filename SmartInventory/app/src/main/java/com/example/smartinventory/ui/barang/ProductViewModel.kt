package com.example.smartinventory.ui.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.Brand
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

    private val _brands = MutableStateFlow<List<Brand>>(emptyList())
    val brands: StateFlow<List<Brand>> = _brands

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
        fetchBrands()
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

    private fun fetchBrands() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getBrands()
                if (response.isSuccessful) _brands.value = response.body() ?: emptyList()
            } catch (e: Exception) {}
        }
    }

    fun addProduct(code: String, name: String, catId: String, brandId: String, supplierId: String, buy: Double, sell: Double, stock: Int, rak: String, color: String, condition: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.insertProduct(code, name, catId, brandId, supplierId, buy, sell, stock, rak, color, condition)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Produk ditambahkan"
                    fetchProducts()
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

    fun editProduct(id: String, code: String, name: String, catId: String, brandId: String, supplierId: String, buy: Double, sell: Double, stock: Int, rak: String, color: String, condition: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.updateProduct(id, code, name, catId, brandId, supplierId, buy, sell, stock, rak, color, condition)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Produk diperbarui"
                    fetchProducts()
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

    fun removeProduct(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.deleteProduct(id)
                if (response.isSuccessful && response.body()?.status == true) {
                    _message.value = "Produk dihapus"
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
