package com.nikhilkhairnar.goodsam.presentation.splash

sealed class SplashUiState {
    object Loading : SplashUiState()
    object NavigateToLogin : SplashUiState()
    data class Error(val message: String) : SplashUiState()
}