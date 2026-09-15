package com.nikhilkhairnar.goodsam.presentation.splash

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.usecase.RegisterDeviceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val registerDeviceUseCase: RegisterDeviceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    fun registerDevice(deviceId: String, modelName: String) {
        viewModelScope.launch {
            _uiState.value = SplashUiState.Loading

            when (val result = registerDeviceUseCase(
                deviceId = deviceId,
                osVersion = Build.VERSION.RELEASE,
                appVersion = "1.0",
                modelName = modelName
            )) {
                is AppResult.Success -> _uiState.value = SplashUiState.NavigateToLogin
                is AppResult.Error -> _uiState.value = SplashUiState.Error(result.message)
            }
        }
    }
}