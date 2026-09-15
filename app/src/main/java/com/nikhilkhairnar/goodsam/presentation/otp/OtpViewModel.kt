package com.nikhilkhairnar.goodsam.presentation.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.usecase.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<OtpUiState>(OtpUiState.Idle)
    val uiState: StateFlow<OtpUiState> = _uiState.asStateFlow()

    fun onVerifyClicked(mobile: String, enteredOtp: String, uniqueId: String) {
        viewModelScope.launch {
            _uiState.value = OtpUiState.Loading


            when (val result = verifyOtpUseCase(
                mobile = mobile,
                otp = enteredOtp,
                password = "",
                uniqueId = uniqueId
            )) {
                is AppResult.Success -> _uiState.value = OtpUiState.NavigateToList
                is AppResult.Error -> _uiState.value = OtpUiState.Error(result.message)
            }
        }
    }
}