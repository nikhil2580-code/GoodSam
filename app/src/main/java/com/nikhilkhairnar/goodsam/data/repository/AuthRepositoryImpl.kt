package com.nikhilkhairnar.goodsam.data.repository

import com.nikhilkhairnar.goodsam.data.remote.ApiService
import com.nikhilkhairnar.goodsam.data.remote.dto.CheckOtpRequest
import com.nikhilkhairnar.goodsam.data.remote.dto.DeviceRequest
import com.nikhilkhairnar.goodsam.data.remote.dto.SignInRequest
import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun registerDevice(
        deviceId: String,
        osVersion: String,
        appVersion: String,
        modelName: String
    ): AppResult<Unit> {
        return try {
            val request = DeviceRequest(
                deviceId = deviceId,
                osVersion = osVersion,
                appVersion = appVersion,
                modelName = modelName
            )
            val response = apiService.registerDevice(request)
            val body = response.body()

            if (response.isSuccessful && body?.error == null) {
                AppResult.Success(Unit)
            } else {
                AppResult.Error(
                    message = body?.error?.message ?: "Device registration failed",
                    code = body?.error?.code
                )
            }
        } catch (e: Exception) {
            AppResult.Error(message = e.message ?: "Network error during device registration")
        }
    }

    override suspend fun signIn(mobile: String): AppResult<Int> {
        return try {
            val request = SignInRequest(mobile = mobile, smsAutofill = "")
            val response = apiService.signIn(request)
            val body = response.body()
            val otp = body?.data?.otp

            if (response.isSuccessful && body?.error == null && otp != null) {
                AppResult.Success(otp)
            } else {
                AppResult.Error(
                    message = body?.error?.message ?: "Sign in failed",
                    code = body?.error?.code
                )
            }
        } catch (e: Exception) {
            AppResult.Error(message = e.message ?: "Network error during sign in")
        }
    }

    override suspend fun verifyOtp(
        mobile: String,
        otp: String,
        password: String,
        uniqueId: String
    ): AppResult<Unit> {
        return try {
            // NOTE: 'password' field behavior is unconfirmed — waiting on API owner's
            // clarification. Both "" and the OTP value itself have returned
            // {"error":{"code":3,"message":"Invalid Passward"}} in testing.
            val request = CheckOtpRequest(
                mobile = mobile,
                otp = otp,
                password = password,
                uniqueId = uniqueId
            )
            val response = apiService.checkOtp(request)
            val body = response.body()

            if (response.isSuccessful && body?.error == null) {
                AppResult.Success(Unit)
            } else {
                AppResult.Error(
                    message = body?.error?.message ?: "OTP verification failed",
                    code = body?.error?.code
                )
            }
        } catch (e: Exception) {
            AppResult.Error(message = e.message ?: "Network error during OTP verification")
        }
    }
}