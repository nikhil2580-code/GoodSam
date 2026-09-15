package com.nikhilkhairnar.goodsam.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    @SerializedName("users") val users: List<UserDto>,
    @SerializedName("total") val total: Int?,
    @SerializedName("skip") val skip: Int?,
    @SerializedName("limit") val limit: Int?
)

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("age") val age: Int?
)