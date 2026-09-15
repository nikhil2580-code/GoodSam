package com.nikhilkhairnar.goodsam.domain.repository

import com.nikhilkhairnar.goodsam.domain.model.AppResult

interface AuthRepository {
    suspend fun registerDevice(deviceId: String, osVersion: String, appVersion: String, modelName: String): AppResult<Unit>
    suspend fun signIn(mobile: String): AppResult<Int> // returns OTP
    suspend fun verifyOtp(mobile: String, otp: String, password: String, uniqueId: String): AppResult<Unit>
}