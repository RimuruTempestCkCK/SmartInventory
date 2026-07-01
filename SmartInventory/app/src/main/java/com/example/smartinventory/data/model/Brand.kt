package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String
)
