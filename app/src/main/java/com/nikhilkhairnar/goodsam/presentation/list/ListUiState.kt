package com.nikhilkhairnar.goodsam.presentation.list

import com.nikhilkhairnar.goodsam.domain.model.User

sealed class ListUiState {
    object Loading : ListUiState()
    data class Success(val users: List<User>) : ListUiState()
    data class Error(val message: String) : ListUiState()
}