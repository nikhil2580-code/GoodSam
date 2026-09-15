package com.nikhilkhairnar.goodsam.data.repository

import com.nikhilkhairnar.goodsam.data.remote.ApiService
import com.nikhilkhairnar.goodsam.data.remote.dto.UserDto
import com.nikhilkhairnar.goodsam.domain.model.AppResult
import com.nikhilkhairnar.goodsam.domain.model.User
import com.nikhilkhairnar.goodsam.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): AppResult<List<User>> {
        return try {
            val response = apiService.getUsers()

            if (response.isSuccessful) {
                val users = response.body()?.users?.map { it.toDomain() } ?: emptyList()
                AppResult.Success(users)
            } else {
                AppResult.Error(
                    message = response.errorBody()?.string() ?: "Failed to fetch users",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            AppResult.Error(message = e.message ?: "Network error while fetching users")
        }
    }

    private fun UserDto.toDomain(): User {
        return User(
            id = id,
            fullName = "${firstName.orEmpty()} ${lastName.orEmpty()}".trim(),
            email = email,
            phone = phone,
            imageUrl = image,
            age = age
        )
    }
}