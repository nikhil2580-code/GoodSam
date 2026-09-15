package com.nikhilkhairnar.goodsam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DeviceRequest(
    @SerializedName("uniqueId") val uniqueId: String = "0",
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("osVersion") val osVersion: String,
    @SerializedName("osName") val osName: String = "Android",
    @SerializedName("devicePlatform") val devicePlatform: String = "Android",
    @SerializedName("appVersion") val appVersion: String,
    @SerializedName("deviceTimezone") val deviceTimezone: String = "",
    @SerializedName("deviceCurrentTimestamp") val deviceCurrentTimestamp: String = "",
    @SerializedName("token") val token: String = "",
    @SerializedName("modelName") val modelName: String
)