package com.example.auth.data

import com.example.auth.domain.AuthRepository
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.asEmptyDataResult

class AuthRepositoryImpl(
    private val authApi: AuthApi,
) : AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = authApi.login(
            LoginRequest(
                email = email,
                password = password
            )
        )

        // TODO: store tokens and use them
        return Result.Success(result).asEmptyDataResult()
    }
}