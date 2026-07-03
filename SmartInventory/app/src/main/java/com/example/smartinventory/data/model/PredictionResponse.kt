package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("hasil") val result: String,
    @SerializedName("probabilitas") val probability: Double,
    @SerializedName("detail") val details: String?,
    @SerializedName("data_historis") val historicalData: String?,
    @SerializedName("stok_cepat_habis") val fastDepleting: List<String>?,
    @SerializedName("status_keamanan") val safetyStatus: String?
)
