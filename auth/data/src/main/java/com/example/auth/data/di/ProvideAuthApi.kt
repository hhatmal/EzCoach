package com.example.auth.data.di

import com.example.auth.data.AuthApi
import retrofit2.Retrofit

fun provideAuthApi(retrofit: Retrofit): AuthApi =
    retrofit.create(AuthApi::class.java)