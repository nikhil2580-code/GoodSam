package com.nikhilkhairnar.goodsam.domain.usecase

import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.model.User
import com.nikhilkhairnar.goodsam.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): AppResult<List<User>> {
        return userRepository.getUsers()
    }
}