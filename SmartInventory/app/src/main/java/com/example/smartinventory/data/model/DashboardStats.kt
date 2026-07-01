package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class DashboardStats(
    @SerializedName("total_barang") val totalProducts: Int,
    @SerializedName("total_brand") val totalBrands: Int,
    @SerializedName("total_supplier") val totalSuppliers: Int,
    @SerializedName("stok_masuk_hari_ini") val stockInToday: Int,
    @SerializedName("stok_keluar_hari_ini") val stockOutToday: Int,
    @SerializedName("barang_limit") val lowStockProducts: List<Product>?
)
