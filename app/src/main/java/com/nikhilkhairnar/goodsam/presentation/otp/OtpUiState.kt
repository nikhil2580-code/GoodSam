package com.nikhilkhairnar.goodsam.presentation.otp

sealed class OtpUiState {
    object Idle : OtpUiState()
    object Loading : OtpUiState()
    object NavigateToList : OtpUiState()
    data class Error(val message: String) : OtpUiState()
}