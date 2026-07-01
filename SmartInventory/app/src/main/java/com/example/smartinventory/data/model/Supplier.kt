package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Supplier(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String?,
    @SerializedName("phone") val phone: String?
)
