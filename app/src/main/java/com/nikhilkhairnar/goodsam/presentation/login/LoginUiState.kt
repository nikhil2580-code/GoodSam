package com.nikhilkhairnar.goodsam.presentation.login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class NavigateToOtp(val mobile: String, val otp: Int) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}