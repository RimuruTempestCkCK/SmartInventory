package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String?,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("category_name") val categoryName: String?,
    
    @SerializedName("brand_id") val brandId: String,
    @SerializedName("brand_name") val brandName: String?,
    
    @SerializedName("supplier_id") val supplierId: String?,
    @SerializedName("supplier_name") val supplierName: String?,
    
    @SerializedName("buy_price") val buyPrice: Double,
    @SerializedName("sell_price") val sellPrice: Double,
    
    @SerializedName("stok") val stock: Int,
    
    @SerializedName("lokasi_rak") val shelfLocation: String?,
    @SerializedName("warna") val color: String?,
    @SerializedName("kondisi") val condition: String?, // "Baru" or "Second"
    
    @SerializedName("gambar") val image: String?,
    @SerializedName("created_at") val createdAt: String?
)
