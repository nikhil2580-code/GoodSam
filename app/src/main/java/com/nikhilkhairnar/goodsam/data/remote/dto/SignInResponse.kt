package com.nikhilkhairnar.goodsam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SignInData(
    @SerializedName("otp") val otp: Int
)

typealias SignInResponse = ApiEnvelope<SignInData>