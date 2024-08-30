package com.example.auth.data

import com.example.core.domain.AuthInfo
import com.example.core.domain.SessionStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authApi: AuthApi,
    private val sessionStorage: SessionStorage
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = sessionStorage.get()?.accessToken

        // TODO: Add token expired check
        if (accessToken != null) {
            val authInfo = sessionStorage.get()

            // Make the token refresh request
            val refreshedToken = runBlocking {
                val response = authApi.refreshAccessToken()
                // Update the refreshed access token and its expiration time in the session
                sessionStorage.set(
                    AuthInfo(
                        accessToken = response.accessToken,
                        refreshToken = authInfo?.refreshToken ?: "",
                        userId = authInfo?.userId ?: ""
                    )
                )
            }

            // Create a new request with the refreshed access token
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $refreshedToken")
                .build()

            // Retry the request with the new access token
            return chain.proceed(newRequest)
        }

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorizedRequest)
    }
}