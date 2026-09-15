package com.nikhilkhairnar.goodsam.domain.usecase

import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterDeviceUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        deviceId: String,
        osVersion: String,
        appVersion: String,
        modelName: String
    ): AppResult<Unit> {
        return authRepository.registerDevice(deviceId, osVersion, appVersion, modelName)
    }
}