package com.nikhilkhairnar.goodsam.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginClicked(mobile: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            when (val result = signInUseCase(mobile)) {
                is AppResult.Success -> {
                    _uiState.value = LoginUiState.NavigateToOtp(mobile = mobile, otp = result.data)
                }
                is AppResult.Error -> {
                    _uiState.value = LoginUiState.Error(result.message)
                }
            }
        }
    }
}