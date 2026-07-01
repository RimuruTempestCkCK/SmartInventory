package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("id_transaksi") val id: String?,
    @SerializedName("id_barang") val productId: String,
    @SerializedName("nama_barang") val productName: String?,
    @SerializedName("tanggal") val date: String,
    @SerializedName("jumlah") val quantity: Int,
    @SerializedName("jenis") val type: String, // "Masuk" or "Keluar"
    @SerializedName("keterangan") val info: String?
)
