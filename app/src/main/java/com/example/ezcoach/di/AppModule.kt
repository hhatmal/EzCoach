package com.example.ezcoach.di

import com.example.auth.data.AuthInterceptor
import com.example.ezcoach.EzCoachApp
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as EzCoachApp).applicationScope
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get(),get()))
            .build()
    }
}