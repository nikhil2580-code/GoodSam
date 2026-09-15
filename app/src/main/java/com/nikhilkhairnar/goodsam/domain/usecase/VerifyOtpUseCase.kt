package com.nikhilkhairnar.goodsam.domain.usecase

import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        mobile: String,
        otp: String,
        password: String,
        uniqueId: String
    ): AppResult<Unit> {
        if (otp.length != 4) {
            return AppResult.Error("Enter a valid 4-digit OTP")
        }
        return authRepository.verifyOtp(mobile, otp, password, uniqueId)
    }
}