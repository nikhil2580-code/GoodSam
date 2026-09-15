package com.nikhilkhairnar.goodsam.domain.usecase

import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(mobile: String): AppResult<Int> {
        if (mobile.length != 10) {
            return AppResult.Error("Enter a valid 10-digit mobile number")
        }
        return authRepository.signIn(mobile)
    }
}