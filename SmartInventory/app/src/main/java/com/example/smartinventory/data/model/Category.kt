package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id_kategori") val id: String?,
    @SerializedName("nama_kategori") val name: String
)
