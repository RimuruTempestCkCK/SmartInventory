package com.example.smartinventory.data.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)
