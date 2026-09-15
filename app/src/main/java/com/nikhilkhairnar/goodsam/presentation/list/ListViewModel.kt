package com.nikhilkhairnar.goodsam.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading

            when (val result = getUsersUseCase()) {
                is AppResult.Success -> _uiState.value = ListUiState.Success(result.data)
                is AppResult.Error -> _uiState.value = ListUiState.Error(result.message)
            }
        }
    }
}