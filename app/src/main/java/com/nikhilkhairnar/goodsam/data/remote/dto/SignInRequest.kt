package com.nikhilkhairnar.goodsam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("otpPass") val otpPass: String = "1",
    @SerializedName("smsAutofill") val smsAutofill: String,
    @SerializedName("user_reg_goodsam_status") val userRegGoodsamStatus: String = "1"
)