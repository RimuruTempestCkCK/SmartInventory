package com.example.smartinventory.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartinventory.data.model.DashboardStats
import com.example.smartinventory.data.model.PredictionResponse
import com.example.smartinventory.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _stats = MutableStateFlow<DashboardStats?>(null)
    val stats: StateFlow<DashboardStats?> = _stats

    private val _prediction = MutableStateFlow<PredictionResponse?>(null)
    val prediction: StateFlow<PredictionResponse?> = _prediction

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val statRes = RetrofitClient.instance.getDashboardStats()
                if (statRes.isSuccessful) _stats.value = statRes.body()

                val predRes = RetrofitClient.instance.getPrediction()
                if (predRes.isSuccessful) _prediction.value = predRes.body()
            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }
}
