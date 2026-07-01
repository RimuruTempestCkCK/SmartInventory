package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id_barang") val id: String?,
    @SerializedName("kode_barang") val code: String,
    @SerializedName("nama_barang") val name: String,
    @SerializedName("id_kategori") val categoryId: String,
    @SerializedName("nama_kategori") val categoryName: String?,
    @SerializedName("id_supplier") val supplierId: String,
    @SerializedName("nama_supplier") val supplierName: String?,
    @SerializedName("harga") val price: Double,
    @SerializedName("stok") val stock: Int
)
