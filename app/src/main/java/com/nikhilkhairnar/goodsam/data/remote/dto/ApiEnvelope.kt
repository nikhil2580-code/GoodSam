package com.nikhilkhairnar.goodsam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code") val code: Int?,
    @SerializedName("message") val message: String?
)

data class ApiEnvelope<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("error") val error: ApiError?
)