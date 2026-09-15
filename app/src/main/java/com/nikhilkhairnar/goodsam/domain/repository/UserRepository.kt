package com.nikhilkhairnar.goodsam.domain.repository

import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.model.User

interface UserRepository {
    suspend fun getUsers(): AppResult<List<User>>
}