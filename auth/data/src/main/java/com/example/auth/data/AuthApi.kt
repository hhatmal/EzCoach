package com.example.auth.data

import com.example.core.data.network.AccessTokenResponse
import retrofit2.http.POST

interface AuthApi {
    @POST("/login")
    suspend fun login(loginRequest: LoginRequest): LoginResponse

    @POST("/refresh")
    suspend fun refreshAccessToken(): AccessTokenResponse
}