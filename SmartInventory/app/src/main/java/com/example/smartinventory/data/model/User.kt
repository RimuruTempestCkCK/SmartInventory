package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id_user") val id: String?,
    @SerializedName("username") val username: String,
    @SerializedName("nama_lengkap") val name: String?,
    @SerializedName("level") val level: String?
)
