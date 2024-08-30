package com.example.auth.data.di

import com.example.auth.data.AuthInterceptor
import com.example.auth.data.AuthRepositoryImpl
import com.example.auth.domain.AuthRepository
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single { provideAuthApi(get()) }
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}