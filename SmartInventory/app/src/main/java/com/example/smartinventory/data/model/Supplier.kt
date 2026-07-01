package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Supplier(
    @SerializedName("id_supplier") val id: String?,
    @SerializedName("nama_supplier") val name: String,
    @SerializedName("alamat") val address: String?,
    @SerializedName("no_hp") val phone: String?
)
