package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id") val id: String?,
    @SerializedName("product_id") val productId: String,
    @SerializedName("product_name") val productName: String?,
    @SerializedName("type") val type: String, // "in", "out", "reject"
    @SerializedName("qty") val quantity: Int,
    @SerializedName("date") val date: String,
    @SerializedName("description") val description: String?
)
