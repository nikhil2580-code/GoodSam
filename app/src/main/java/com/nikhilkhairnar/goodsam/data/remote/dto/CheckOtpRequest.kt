package com.nikhilkhairnar.goodsam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CheckOtpRequest(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("otp") val otp: String,
    @SerializedName("password") val password: String,
    @SerializedName("otpPass") val otpPass: String = "",
    @SerializedName("uniqueId") val uniqueId: String,
    @SerializedName("user_reg_goodsam_status") val userRegGoodsamStatus: String = "1"
)