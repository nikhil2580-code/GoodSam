package com.nikhilkhairnar.goodsam.data.remote

import com.nikhilkhairnar.goodsam.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @POST("device")
    suspend fun registerDevice(@Body request: DeviceRequest): Response<DeviceResponse>

    @POST("signin")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("checkotp")
    suspend fun checkOtp(@Body request: CheckOtpRequest): Response<CheckOtpResponse>

    @GET
    suspend fun getUsers(@Url url: String = "https://dummyjson.com/users"): Response<UsersListResponse>
}